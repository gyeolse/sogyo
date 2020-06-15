//history activity

package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static <Integer, LatLng> Integer getKey(Map<Integer, LatLng> map,  LatLng value) {
        for (Integer key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    //Custom Marker 구현
    View marker_root_view;
    TextView tv_marker;
    // 1. setCustomMarkerView. 액티비티 초기화되는 부분에 미리 뷰 설정할 것.
    private void setCustomMarkerView(){
        marker_root_view = LayoutInflater.from(this).inflate(R.layout.marker,null);
        tv_marker = (TextView)marker_root_view.findViewById(R.id.tv_marker);
    }

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    //For Volley
    private static final String TAG = "MAIN";
    private RequestQueue queue; //volley가 queue에 response 넣기
    public String url = "http://10.0.2.2:3000/history/location"; //보낼 URL.
    public ClusterManager<MyItem> mClusterManager; //clustering을 하기 위한 cluster manager 객체
    public Map<String, GoogleMap.InfoWindowAdapter> adapterMap = new HashMap<>(); //InfoAdapter 넣을 Map 객체

    //같은 위치에 해당하는 가게들을 뭉치기 위해서 생성함
    ArrayList<Integer> Count_list = new ArrayList<>(Collections.nCopies(1500,0));
    HashMap<Integer,LatLng> LatLngMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final LoadingDialog loadingDialog = new LoadingDialog(HistoryActivity.this);
        Handler handler = new Handler();
        loadingDialog.startLoadingDialog();
        //로딩화면 표시
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //volley 큐 초기화
        queue = Volley.newRequestQueue(this); //큐 초기화

        //2 MAP FRAGMENT
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.fragment_main_mv);
        mapFragment.getMapAsync(this);

        //3
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sogyo");

        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);// 사이드 메뉴에 아이콘 색깔을 원래 아이콘 색으로

        drawerLayout = findViewById(R.id.layout_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);//누를때마다 아이콘이 팽그르 돈다.
        drawerToggle.syncState();// 삼선 메뉴 추가
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(HistoryActivity.this, CommercialAnalyze_main.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(HistoryActivity.this, judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(final GoogleMap googleMap){
        final GoogleMap googleMap1 = googleMap;
        mClusterManager = new ClusterManager<>(this,googleMap1);
        //Custom Marker 초기화
        setCustomMarkerView();

//1. Volley를 통해서 서버에서 받고, 위치에 따라 가게들을 묶어주는 작업 처리 과정.
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        double longitude = jsonObject.getDouble("longitude");
                        double latitude = jsonObject.getDouble("latitude");
        //같은 위치에 해당하는 애들 묶어주는 작업
                        LatLng tempLatLng = new LatLng(latitude,longitude);
                        if(i==0){ //처음 INIT
                            LatLngMap.put(i,tempLatLng);
                            Count_list.set(0,1);
                        }
                        else { //i가 0이 아닐 때 무조건 실행이 됨.
                            if(LatLngMap.containsValue(tempLatLng)==true){
                                int tempKey = getKey(LatLngMap,tempLatLng); //같은 value가 있는 key를 탐색
                                int newCnt = Count_list.get(tempKey)+1; //갯수를 +1 카운트해줌.
                                Count_list.set(tempKey,newCnt); //새롭게 카운트된 갯수를 세팅.
                            }
                            else {
                                LatLngMap.put(i, tempLatLng); //i번째에 집어넣어야 함.
                                Count_list.set(i,1); //i번째에 1. 가게 하나가 있다는 뜻.
                            }
                        }
                    }
                    for(int i=0;i<LatLngMap.size();i++){
                        if(Count_list.get(i)!=0){
                            LatLng location = LatLngMap.get(i);
                            String cnt_ = Count_list.get(i).toString()+"개의 점포";
                            MyItem offsetItem = new MyItem(location,cnt_);
                            //수정. 06/16
                            mClusterManager.addItem(offsetItem);
//                            MarkerOptions markerOptions = new MarkerOptions().position(location).title(cnt_);
//                            googleMap1.addMarker(markerOptions);

                        }
                    }
                    //잘되는지 TEST. LOG화면에 출력
//                    for(int i=0;i<LatLngMap.size();i++){
//                        System.out.println("Count List의 크기 "+ Count_list.get(i));
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { } });
        queue.add(jsonArrayRequest); //queue에 request 추가

        //BASE LOCATION 세팅
        LatLng base_location = new LatLng(37.451095, 126.656996);
        googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(base_location, 15));

        //Clustering 세팅
        googleMap1.setOnCameraIdleListener(mClusterManager);
        googleMap1.setOnMarkerClickListener(mClusterManager);

        //Custom Marker 세팅하는 과정.
        mClusterManager.setRenderer(new MarkerRenderer(getApplicationContext(),googleMap1,mClusterManager));
        mClusterManager.setOnClusterItemClickListener(
                new ClusterManager.OnClusterItemClickListener<MyItem>() {
                    @Override
                    public boolean onClusterItemClick(MyItem myItem) {
                        Toast.makeText(HistoryActivity.this,myItem.cnt+"이력이 있습니다. ",Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(HistoryActivity.this, HistoryDetailInfo.class);
                        double a = myItem.getPosition().latitude;
                        double b = myItem.getPosition().longitude;
                        myIntent.putExtra("longitude",a);
                        myIntent.putExtra("latitude",b);
                        startActivity(myIntent);
                        return false;

                    }
                }
        );



    } //ONMAPREADY 종료 시점.

} //HISTORY ACTIVITY 종료 시점