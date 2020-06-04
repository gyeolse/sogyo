package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class CommercialAnalyze_main extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    //For Volley
    private static final String TAG = "MAIN";
    private RequestQueue queue; //volley가 queue에 response 넣기
    public String url = "http://10.0.2.2:3000/CommercialAnalyze_main"; //보낼 URL. 임의로 생성
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoadingDialog loadingDialog = new LoadingDialog(CommercialAnalyze_main.this);
        Handler handler = new Handler();
        loadingDialog.startLoadingDialog();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_analyze_main);
        queue = Volley.newRequestQueue(this); //큐 초기화
        //2 MAP FRAGMENT
        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.commercial_main_mv);
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
                        Intent intent = new Intent(CommercialAnalyze_main.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(CommercialAnalyze_main.this, CommercialAnalyze_main.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(CommercialAnalyze_main.this, HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(CommercialAnalyze_main.this, judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        Button button_commercialAnalyze=(Button)findViewById(R.id.detail_btn);
        button_commercialAnalyze.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommercialAnalyze_main.this,CommercialAnalyze.class);
                startActivity(intent);
            }
        });
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);//response는 일단 임의의값으로 놔두었고 서버에 등급값요청해서 받은걸로 처리해야함

                int resID=getResources().getIdentifier("tv_"+response,"id","com.lgj.sogyo");

                TextView tv=findViewById(resID);
                tv.setBackgroundColor(Color.WHITE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setTag(TAG);
        queue.add(stringRequest);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap googleMap1 = googleMap;
        LatLng base_location = new LatLng(37.451095, 126.656996);
        googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(base_location, 15));
    }

}