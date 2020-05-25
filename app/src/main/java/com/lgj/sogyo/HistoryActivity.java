package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    NavigationView navigationView;
    DrawerLayout  drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //2
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.fragment_main_mv);
        mapFragment.getMapAsync(this);

        //3
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Sogyo");

        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);// 사이드 메뉴에 아이콘 색깔을 원래 아이콘 색으로

        drawerLayout=findViewById(R.id.layout_drawer);
        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);//누를때마다 아이콘이 팽그르 돈다.
        drawerToggle.syncState();// 삼선 메뉴 추가

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_home:
                        Intent intent = new Intent(HistoryActivity.this,MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(HistoryActivity.this,CommercialAnalyze.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(HistoryActivity.this,HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(HistoryActivity.this,judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        String[] namelist = {"틈새","봉구비어", "딱한잔","방가네","하나은행","회사랑조개사랑","칸","소야돼지꿈꿔","돈토","OST"};
        Double[] longitude = {126.6578959, 126.6568304, 126.6645063, 126.65616, 126.6596613,
                            126.66209, 126.6571828, 126.6583013, 126.6571828, 126.6578075};
        Double[] latitude = {37.4531151, 37.45202175, 37.45000935, 37.45601471, 37.44791816, 37.4498829,
                        37.45190862, 37.45129962, 37.45190862, 37.45236624} ;

        for(int i=0;i<10;i++){
            LatLng location = new LatLng(latitude[i],longitude[i]);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title("인하대상권")
                    .snippet(namelist[i])
                    .position(location);
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));
//
//            View infoWindow = getLayoutInflater().inflate(R.layout.hitsoryinfo,null);
//            DriverInfoAdapter driverInfoAdapter = new DriverInfoAdapter(infoWindow);
//            googleMap.setInfoWindowAdapter(driverInfoAdapter);

        }


        //마커를 비추고 있는 화면을 비추는 카메라를 띄워준다고 생각하자.
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,16));

    }
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
