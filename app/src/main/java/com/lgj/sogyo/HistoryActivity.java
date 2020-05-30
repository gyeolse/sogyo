package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    //For Volley
    private static final String TAG = "MAIN";
    private RequestQueue queue; //volley가 queue에 response를 넣어주고, 그거를 차례때로 뽑아서 서버에 보내는 구조임

    public String url = "http://10.0.2.2:3000/history/location"; //요청 보낼 url 현재 지금 있는건 임의로 만든 거임.
    public ArrayList<Double> latitude_list = new ArrayList<>();
    public ArrayList<Double> longitude_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //FOR VOLLEY
        queue = Volley.newRequestQueue(this); //큐 초기화

////////////////////////////////////////////////////////////////////////////////////////////
        //2
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
                        intent = new Intent(HistoryActivity.this, CommercialAnalyze.class);
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
    public void onMapReady(GoogleMap googleMap){
        final GoogleMap googleMap1 = googleMap;

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
                    longitude_list.add(longitude);
                    latitude_list.add(latitude);

                    LatLng location = new LatLng(latitude_list.get(i), longitude_list.get(i));
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(location);
                    googleMap1.addMarker(markerOptions).setTitle(BizName);
                    googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    });
    queue.add(jsonArrayRequest);
    LatLng location_2 = new LatLng( 37.45155845, 126.6572908);

    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location_2, 16));
//
//            View infoWindow = getLayoutInflater().inflate(R.layout.hitsoryinfo,null);
//            DriverInfoAdapter driverInfoAdapter = new DriverInfoAdapter(infoWindow);
//            googleMap.setInfoWindowAdapter(driverInfoAdapter);
    }


    //마커를 비추고 있는 화면을 비추는 카메라를 띄워준다고 생각하자.
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));

}

/*
            ArrayList<Store> storedata = new ArrayList<>();
        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.store_data));
        BufferedReader reader= new BufferedReader(is);
        CSVReader read = new CSVReader(reader);
        String nextLine = null;
        String line = null;
        int count = 0;
        try {
            while ((nextLine = reader.readLine()) != null) {
                String[] lineContents = nextLine.split(",");

                Store store = new Store();
//                store.setStoreNo(Integer.parseInt(lineContents[0]));
                store.setBizName(lineContents[1]);
                store.setUpperCategory(lineContents[2]);
                store.setLowerCategory(lineContents[3]);
                store.setAddress(lineContents[4]);
  //              store.setFloor(Integer.parseInt(lineContents[5]));
                store.setLongitude(Double.parseDouble(lineContents[6]));
                store.setLatitude(Double.parseDouble(lineContents[7]));
   //             store.setIsOpen(Integer.parseInt(lineContents[8]));
                store.setOpenYear(lineContents[9]);
                store.setCloseYear(lineContents[10]);
//                store.setSales(Integer.parseInt(lineContents[11]));
//                store.setIsFrancise(Integer.parseInt(lineContents[12]));
//                store.setBizZone_localNo(Integer.parseInt(lineContents[13]));

//                storedata.set(count,store);
//                count++;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        //Store에 Data를 넣어줌.
        //넣어준


* */
