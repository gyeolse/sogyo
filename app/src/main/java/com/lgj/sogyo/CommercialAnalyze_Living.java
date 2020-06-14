package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CommercialAnalyze_Living extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private static final String TAG = "MAIN";
    //                                ArrayList<Entry> arr[] = new ArrayList[21];

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_analyze__living);
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
                        Intent intent = new Intent(CommercialAnalyze_Living.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(CommercialAnalyze_Living.this, CommercialAnalyze_main.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(CommercialAnalyze_Living.this, HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(CommercialAnalyze_Living.this, judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        queue=Volley.newRequestQueue(this);
        Spinner spinner=(Spinner)findViewById(R.id.spinner_living);
        spinner.setPrompt("Select Option");

        ArrayAdapter Adapter = ArrayAdapter.createFromResource(this,
                R.array.Living_Option, android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(Adapter);

        final myMarkerView mv=new myMarkerView(this,R.layout.activity_my_marker_view);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.chart_living_byyear).setVisibility(View.INVISIBLE);
                findViewById(R.id.chart_living_byage).setVisibility(View.INVISIBLE);
                findViewById(R.id.chart_living_bygender).setVisibility(View.INVISIBLE);

                if(position==0){

                    String url="http://10.0.2.2:3000/CommercialAnalyze/livingbyyear";
                    final JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                            Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                ArrayList<Entry>arr=new ArrayList<>();
                                String Label="연도별 상주인구";
                                LineChart chart = findViewById(R.id.chart_living_byyear);
                                chart.setVisibility(View.VISIBLE);
                                for(int i=0;i<response.length();i++){
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int year = jsonObject.getInt("year");
                                    System.out.println(year);
                                    int population = jsonObject.getInt("pop");
                                    arr.add(new Entry(Float.valueOf(year),Float.valueOf(population)));

                                }

                                ArrayList<Entry>arr2=new ArrayList<>();
                                for(int i=0;i<arr.size();i++){
                                    arr2.add(arr.get(arr.size()-i-1));
                                }
                                LineDataSet set = new LineDataSet(arr2,Label);

                                LineData chartData=new LineData();
                                int c= Color.RED;
                                set.setColor(c);

                                set.setCircleColors(c);
                                set.setLineWidth(2);
                                set.setValueTextSize(10);
                                chartData.addDataSet(set);
                                System.out.println("3");
                                chart.setBackgroundColor(Color.WHITE);


                                chartData.setHighlightEnabled(true);
                                chartData.setDrawValues(false);
                                chart.setData(chartData);
                                chart.setDrawBorders(true);
                                chart.setBorderWidth(2);
                                chart.setMarkerView(mv);
                                chart.setDrawMarkerViews(true);
                                System.out.println("4");
                                XAxis xAxis=chart.getXAxis();
                                xAxis.setLabelCount(4);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                                YAxis yLAxis=chart.getAxisLeft();
                                yLAxis.setAxisMaximum(25000);
                                yLAxis.setAxisMinimum(15000);
                                yLAxis.setLabelCount(2);
                                YAxis yRAxis=chart.getAxisRight() ;
                                yRAxis.setAxisMaximum(25000);
                                yRAxis.setAxisMinimum(15000);
                                yRAxis.setLabelCount(2);
                                Legend legend = chart.getLegend();
                                legend.setEnabled(false);

                                System.out.println("5");
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
                if(position==1){
                    String url="http://10.0.2.2:3000/CommercialAnalyze/livingbyage";
                    final JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                            Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
                        @Override
                        public void onResponse(JSONArray response) {
                            try {

                                List<BarEntry> arr[]=new ArrayList[5];
                                String Label="나이대별 상주인구";
                                BarChart chart = (BarChart)findViewById(R.id.chart_living_byage);
                                for(int i=0;i<response.length();i++){
                                    arr[i]=new ArrayList<>();
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int population = jsonObject.getInt("pop");
                                    arr[i].add(new BarEntry(Float.valueOf(i),Float.valueOf(population)));
                                }
                                chart.setVisibility(View.VISIBLE);
                                BarDataSet ten =new BarDataSet(arr[0],"20대 미만");
                                ten.setValueTextSize(10);
                                BarDataSet twenty =new BarDataSet(arr[1],"20대");
                                twenty.setValueTextSize(10);
                                BarDataSet thirty =new BarDataSet(arr[2],"30대");
                                thirty.setValueTextSize(10);
                                BarDataSet fourty =new BarDataSet(arr[3],"40대");
                                fourty.setValueTextSize(10);
                                BarDataSet fifty =new BarDataSet(arr[4],"50대 이상");
                                fifty.setValueTextSize(10);
                                ten.setColor(Color.RED);
                                twenty.setColor(Color.GREEN);
                                thirty.setColor(Color.GRAY);
                                fourty.setColor(Color.BLACK);
                                fifty.setColor(Color.BLUE);
                                BarData chartData=new BarData();
                                chartData.addDataSet(ten);
                                chartData.addDataSet(twenty);
                                chartData.addDataSet(thirty);
                                chartData.addDataSet(fourty);
                                chartData.addDataSet(fifty);
                                chartData.setBarWidth(0.9f);
                                chart.setBackgroundColor(Color.WHITE);


                                chartData.setHighlightEnabled(true);
//                                chartData.setDrawValues(false);
                                chart.setData(chartData);
                                chart.setDrawBorders(true);
                                chart.setBorderWidth(2);
                                chart.setFitBars(true);
                                XAxis xAxis=chart.getXAxis();
                                xAxis.setDrawLabels(false);

                                YAxis yLAxis=chart.getAxisLeft();
                                yLAxis.setAxisMinimum(0);
                                yLAxis.setLabelCount(5);
                                YAxis yRAxis=chart.getAxisRight() ;
                                yRAxis.setAxisMinimum(0);
                                yRAxis.setLabelCount(5);


                                Legend legend = chart.getLegend();
                                legend.setEnabled(true);

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
                if(position==2){
                    String url="http://10.0.2.2:3000/CommercialAnalyze/livingbygender";
                    final JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(
                            Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
                        @Override
                        public void onResponse(JSONArray response) {
                            try {

                                List<BarEntry> arr[]=new ArrayList[2];


                                BarChart chart = (BarChart)findViewById(R.id.chart_living_bygender);
                                chart.setVisibility(View.VISIBLE);

                                for(int i=0;i<response.length();i++){
                                    arr[i]=new ArrayList<>();
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    int population = jsonObject.getInt("pop");

                                    arr[i].add(new BarEntry(Float.valueOf(i),Float.valueOf(population)));
                                }
                                BarDataSet woman=new BarDataSet(arr[1],"woman");
                                BarDataSet man=new BarDataSet(arr[0],"man");
                                BarData chartData=new BarData();
//                                BarData chartData=new BarData(woman);

                                int c= Color.RED;
                                woman.setColor(c);
                                man.setColor(Color.BLUE);

                                woman.setValueTextSize(10);
                                man.setValueTextSize(10);


                                chartData.addDataSet(man);
                                chartData.addDataSet(woman);
                                chartData.setHighlightEnabled(true);
//                                chartData.setDrawValues(false);
//                                chartData.setBarWidth(1f);
                                chart.setData(chartData);
                                chart.setDrawBorders(true);
                                chart.setBorderWidth(2);
                                chart.setFitBars(true);
                                chart.setBackgroundColor(Color.WHITE);
                                XAxis xAxis=chart.getXAxis();
                                xAxis.setDrawLabels(false);
//                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                                chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisLabel));
                                YAxis yLAxis=chart.getAxisLeft();
                                yLAxis.setAxisMinimum(0);
                                yLAxis.setLabelCount(5);
                                YAxis yRAxis=chart.getAxisRight() ;
                                yRAxis.setAxisMinimum(0);
                                yRAxis.setLabelCount(5);



                                Legend legend = chart.getLegend();
                                legend.setEnabled(true);
                                legend.setTextColor(Color.BLACK);
                                legend.setTextSize(10);

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