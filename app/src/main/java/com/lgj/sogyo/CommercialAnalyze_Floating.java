package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;
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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class CommercialAnalyze_Floating extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private static final String TAG = "MAIN";

    RequestQueue queue;
    boolean mInitspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_analyze__floating);
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
                        Intent intent = new Intent(CommercialAnalyze_Floating.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(CommercialAnalyze_Floating.this, CommercialAnalyze_main.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(CommercialAnalyze_Floating.this, HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(CommercialAnalyze_Floating.this, judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        queue=Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:3000/CommercialAnalyze_Floating";
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setPrompt("Select Option");

        ArrayAdapter Adapter = ArrayAdapter.createFromResource(this,
                R.array.Floating_Option, android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(Adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    String url="http://10.0.2.2:3000/CommercialAnalyze/bytime";
                    final JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                            Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                ArrayList<Entry>arr=new ArrayList<>();
                                String Label="시간대 별 유동인구";
                                LineChart chart = findViewById(R.id.chart);
                                for(int i=0;i<response.length();i++){
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int time = jsonObject.getInt("time");
                                    int population = jsonObject.getInt("pop");
                                    arr.add(new Entry(Float.valueOf(time),Float.valueOf(population)));

                                }
                                LineDataSet set = new LineDataSet(arr,Label);
                                LineData chartData=new LineData();
                                int c= Color.RED;
                                set.setColor(c);
                                set.setCircleColors(c);
                                set.setLineWidth(2);
                                set.setValueTextSize(15);
                                chartData.addDataSet(set);
                                chart.setBackgroundColor(Color.WHITE);
                                chart.setData(chartData);
                                chart.setDrawBorders(true);
                                chart.setBorderWidth(2);
                                XAxis xAxis=chart.getXAxis();
                                xAxis.setLabelCount(3);
                                YAxis yLAxis=chart.getAxisLeft();
                                yLAxis.setAxisMaximum(8000);
                                yLAxis.setAxisMinimum(2000);
                                yLAxis.setLabelCount(4);
                                YAxis yRAxis=chart.getAxisRight() ;
                                yRAxis.setAxisMaximum(8000);
                                yRAxis.setAxisMinimum(2000);
                                yRAxis.setLabelCount(4);
                                Legend legend = chart.getLegend();
                                legend.setEnabled(true);
                                legend.setTextColor(Color.RED);
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}