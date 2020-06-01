package com.lgj.sogyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.YAxis;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import android.graphics.Color;
public class CommercialAnalyze extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private static final String TAG = "MAIN";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_analyze);
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
                        Intent intent = new Intent(CommercialAnalyze.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(CommercialAnalyze.this, CommercialAnalyze.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(CommercialAnalyze.this, HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(CommercialAnalyze.this, judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });


        //tv = findViewById(R.id.tvMain);
        queue= Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:3000/CommercialAnalyze";


//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                tv.setText(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });


//        ArrayList<Entry> fastfood = new ArrayList<>();
//        ArrayList<Entry> cafe = new ArrayList<>();
//        ArrayList<Entry> bakery = new ArrayList<>();
//        ArrayList<Entry> pizza = new ArrayList<>();
//        ArrayList<Entry> icecream = new ArrayList<>();
//        ArrayList<Entry> koreanfood = new ArrayList<>();
//        ArrayList<Entry> ramen = new ArrayList<>();
//        ArrayList<Entry> dosirak = new ArrayList<>();
//        ArrayList<Entry> waterrice = new ArrayList<>();
//        ArrayList<Entry> ddeok = new ArrayList<>();
//        ArrayList<Entry> noodles = new ArrayList<>();
//        ArrayList<Entry> chinafood = new ArrayList<>();
//        ArrayList<Entry> japanfood = new ArrayList<>();
//        ArrayList<Entry> galbi = new ArrayList<>();
//        ArrayList<Entry> chicken = new ArrayList<>();
//        ArrayList<Entry> thai = new ArrayList<>();
//        ArrayList<Entry> soju = new ArrayList<>();
//        ArrayList<Entry> jokbal = new ArrayList<>();
//        ArrayList<Entry> duck = new ArrayList<>();
//        ArrayList<Entry> gobchang = new ArrayList<>();
////        arr[0].add(new Entry(1,2));
////        System.out.println(arr[0].get(0));
//        final ArrayList<Integer>array[]=new ArrayList[20];
//        array[0].add(1);
//        System.out.println(array[0].get(0));
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<Entry> arr[] = new ArrayList[21];
                    ArrayList<String>label=new ArrayList<>();
                    LineChart chart = findViewById(R.id.chart);
                    for(int i=0;i<response.length();i++){

                        JSONObject jsonObject = response.getJSONObject(i);
                        if(i%4==0){
                            label.add(jsonObject.getString("lowerCategory"));
                            arr[i/4]=new ArrayList<>();
                        }
                        int quarter = jsonObject.getInt("quarter");
                        int qt_sales = jsonObject.getInt("qt_sales");
                        arr[i/4].add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                    }

//
//                    label.add("패스트푸드");
//                    label.add("커피전문점/카페/다방");
//                    System.out.println(arr[0].get(0));

                    LineDataSet set[]=new LineDataSet[21];

                    Random generator=new Random();

                    LineData chartData=new LineData();
                    for(int i=0;i<21;i++){
                        set[i]=new LineDataSet(arr[i],label.get(i));
                        int r=generator.nextInt(256);
                        int g=generator.nextInt(256);
                        int b=generator.nextInt(256);
                        int c= Color.rgb(r,g,b);
                        set[i].setColor(c);
                        set[i].setCircleColor(c);
                        chartData.addDataSet(set[i]);
                    }
//
//                    LineDataSet set1=new LineDataSet(arr[0],"패스트푸드");
//                    set1.setColor(Color.BLACK);
//                    set1.setCircleColor(Color.BLACK);
//                    LineDataSet set2 =new LineDataSet(arr[1],"커피전문점/카페/다방");
//                    set2.setColor(Color.BLUE);
//                    set2.setColor(Color.BLUE);
//                    LineDataSet set3 =new LineDataSet(arr[2],"제과점");
//                    set3.setColor(Color.CYAN);
//                    set3.setColor(Color.CYAN);
//                    LineDataSet set4 =new LineDataSet(arr[2],"제과점");
//                    set4.setColor(Color.CYAN);
//                    set4.setColor(Color.CYAN);
//
//
//
//
//
//
//
//
//
//
//                    chartData.addDataSet(set1);
//                    chartData.addDataSet(set2);
                    chart.setData(chartData);
                    chart.setVisibleYRange(2000,7000, YAxis.AxisDependency.LEFT);
                    chart.invalidate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }
        );
        queue.add(jsonArrayRequest);



    }

}
