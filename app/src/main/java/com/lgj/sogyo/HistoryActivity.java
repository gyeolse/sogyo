//history activity

package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    //For Volley
    private static final String TAG = "MAIN";
    private RequestQueue queue; //volley가 queue에 response 넣기
    public String url = "http://10.0.2.2:3000/history/location"; //보낼 URL.
    public ArrayList<Double> latitude_list = new ArrayList<>();
    public ArrayList<Double> longitude_list = new ArrayList<>();
    public ClusterManager<MyItem> mClusterManager; //clustering을 하기 위한 cluster manager 객체
    public Map<String, GoogleMap.InfoWindowAdapter> adapterMap = new HashMap<>(); //InfoAdapter 넣을 Map 객체

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

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        double longitude = jsonObject.getDouble("longitude");
                        double latitude = jsonObject.getDouble("latitude");
                        String BizName = jsonObject.getString("BizName");
                        String upperCategory = jsonObject.getString("upperCategory");
                        String floor = jsonObject.getString("floor");
                        int IsOpen = jsonObject.getInt("IsOpen");
                        String openYear = jsonObject.getString("openYear").substring(0,7);
                        String closeYear = jsonObject.getString("closeYear");
                        if(floor=="null"){floor="1";}
                        String Isopenstr="";
                        if(IsOpen==1){
                            Isopenstr= "O";
                        }
                        else{
                            Isopenstr="X";
                        }
                        if(closeYear!="null"){ closeYear=closeYear.substring(0,7); }
                        else{ closeYear=""; }
/////////////////////////INFO adapter 를 위한 과정
                        MyItem offsetItem = new MyItem(latitude,longitude,BizName,upperCategory,floor,Isopenstr,openYear,closeYear);
                        LatLng latLng1 = new LatLng(latitude,longitude);

                        MarkerOptions markerOptions = new MarkerOptions().position(latLng1).title(BizName);
                        Marker marker = googleMap1.addMarker(markerOptions);
                        View infoWindow = getLayoutInflater().inflate(R.layout.historyinfo_2,null);
                        DriverInfoAdapter driverInfoAdapter = new DriverInfoAdapter(infoWindow,offsetItem);
                        CentralInfoWindowAdapter centralInfoWindowAdapter = new CentralInfoWindowAdapter(adapterMap);

                        adapterMap.put(marker.getId(),driverInfoAdapter);
                        googleMap1.setInfoWindowAdapter(centralInfoWindowAdapter);
////////////////////////////////////////////////////////////////////////////

                        googleMap1.setOnCameraIdleListener(mClusterManager);
                        googleMap1.setOnMarkerClickListener(mClusterManager);
                        mClusterManager.addItem(offsetItem);

//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(location);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { } });

        //Clustering
        LatLng base_location = new LatLng(37.451095, 126.656996);
        googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(base_location, 15));
        googleMap1.setOnCameraIdleListener(mClusterManager);
        googleMap1.setOnMarkerClickListener(mClusterManager);

        queue.add(jsonArrayRequest); //queue에 request 추가
    } //ONMAPREADY 종료 시점.
} //HISTORY ACTIVITY 종료 시점