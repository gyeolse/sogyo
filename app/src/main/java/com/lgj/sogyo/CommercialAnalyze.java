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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class CommercialAnalyze extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ListView listview;
    ArrayAdapter Adapter;
    private static final String TAG = "MAIN";
    LineChart chart;
    LineData chartData;
    RequestQueue queue;
    boolean mInitspinner;
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
                        intent = new Intent(CommercialAnalyze.this, CommercialAnalyze_main.class);
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

//        String url = "http://10.0.2.2:3000/CommercialAnalyze";

    Button apply=(Button)findViewById(R.id.apply);
        String[]items=getResources().getStringArray(R.array.Category);

        listview=(ListView)findViewById(R.id.lst);
        Adapter = ArrayAdapter.createFromResource(this, R.array.Category, android.R.layout.simple_list_item_multiple_choice);

        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,items));
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(Adapter);
////        Spinner spinner = (Spinner)findViewById(R.id.spinner);
//        ArrayAdapter Adapter = ArrayAdapter.createFromResource(this,
//                R.array.Category, android.R.layout.simple_spinner_item);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(Adapter);
        apply.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                System.out.println("1");
                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = Adapter.getCount() ;
                chart = findViewById(R.id.chart);
                chartData=new LineData();
                for(int i = count-1;i>=0;i--){
                    if(checkedItems.get(i)){
                        if(i==0){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/cafe";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="커피전문점/카페/다방";

                                        for(int i=0;i<response.length();i++){
                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);
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
                        if(i==1){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/fastfood";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="패스트푸드";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==2){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/korean";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="한식/백반/한정식";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==3){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/noodle";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="국수/만두/칼국수";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==4){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/chicken";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="후라이드/양념치킨";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==5){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/gobchang";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="곱창/양구이전문";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==6){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/ramen";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="라면김밥분식";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==7){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/china";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="중국음식/중국집";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==8){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/thai";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="동남아음식";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==9){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/bakery";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="제과점";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==10){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/dosirak";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="도시락전문";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==11){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/west";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="양식";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==12){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/drink";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="유흥주점";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==13){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/pizza";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="피자전문";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==14){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/waterrice";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="죽전문점";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==15){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/japan";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="japan";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==16){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/jokbal";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="족발/보쌈전문";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==17){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/icecream";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="아이스크림판매";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==18){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/ddeok";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="떡볶이전문";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==19){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/galbi";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="갈비/삼겹살";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                        if(i==20){
                            String url = "http://10.0.2.2:3000/CommercialAnalyze/duck";
                            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                    Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
//                                ArrayList<Entry> arr[] = new ArrayList[21];
                                        ArrayList<Entry>arr=new ArrayList<>();
                                        String Label="닭/오리요리";
                                        for(int i=0;i<response.length();i++){

                                            JSONObject jsonObject = response.getJSONObject(i);
                                            int quarter = jsonObject.getInt("quarter");
                                            int qt_sales = jsonObject.getInt("qt_sales");
                                            arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));

                                        }
                                        LineDataSet set = new LineDataSet(arr,Label);
                                        Random rand=new Random();
                                        int c= Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
                                        set.setColor(c);
                                        set.setCircleColors(c);
                                        set.setLineWidth(2);
                                        set.setValueTextSize(15);
                                        chartData.addDataSet(set);

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
                }

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
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
                    }
                },1000);
            }
        });
//
//        listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position==0){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/cafe";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="커피전문점/카페/다방";
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//
//
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==1){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/fastfood";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="패스트푸드";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==2){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/korean";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="한식/백반/한정식";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==3){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/noodle";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="국수/만두/칼국수";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==4){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/chicken";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="후라이드/양념치킨";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==5){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/gobchang";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="곱창/양구이전문";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==6){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/ramen";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="라면김밥분식";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==7){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/china";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="중국음식/중국집";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==8){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/thai";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="동남아음식";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==9){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/bakery";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="제과점";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==10){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/dosirak";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="도시락전문";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==11){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/west";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="양식";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==12){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/drink";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="유흥주점";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==13){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/pizza";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="피자전문";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==14){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/waterrice";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="죽전문점";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==15){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/japan";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="일식/수산물";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==16){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/jokbal";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="족발/보쌈전문";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==17){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/icecream";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="아이스크림판매";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==18){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/ddeok";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="떡볶이전문";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==19){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/galbi";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="갈비/삼겹살";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//                if(position==20){
//                    String url = "http://10.0.2.2:3000/CommercialAnalyze/duck";
//                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                            Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
////                                ArrayList<Entry> arr[] = new ArrayList[21];
//                                ArrayList<Entry>arr=new ArrayList<>();
//                                String Label="닭/오리요리";
//
//                                LineChart chart = findViewById(R.id.chart);
//                                for(int i=0;i<response.length();i++){
//
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    int quarter = jsonObject.getInt("quarter");
//                                    int qt_sales = jsonObject.getInt("qt_sales");
//                                    arr.add(new Entry(Float.valueOf(quarter),Float.valueOf(qt_sales)));
//
//                                }
//                                LineDataSet set = new LineDataSet(arr,Label);
//                                LineData chartData=new LineData();
//                                int c= Color.RED;
//                                set.setColor(c);
//                                set.setCircleColors(c);
//                                set.setLineWidth(2);
//                                set.setValueTextSize(15);
//                                chartData.addDataSet(set);
//                                chart.setBackgroundColor(Color.WHITE);
//                                chart.setData(chartData);
//                                chart.setDrawBorders(true);
//                                chart.setBorderWidth(2);
//                                XAxis xAxis=chart.getXAxis();
//                                xAxis.setLabelCount(3);
//                                YAxis yLAxis=chart.getAxisLeft();
//                                yLAxis.setAxisMaximum(8000);
//                                yLAxis.setAxisMinimum(2000);
//                                yLAxis.setLabelCount(4);
//                                YAxis yRAxis=chart.getAxisRight() ;
//                                yRAxis.setAxisMaximum(8000);
//                                yRAxis.setAxisMinimum(2000);
//                                yRAxis.setLabelCount(4);
//                                Legend legend = chart.getLegend();
//                                legend.setEnabled(true);
//                                legend.setTextColor(Color.RED);
//                                chart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//
//                    }
//                    );
//                    queue.add(jsonArrayRequest);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//

    }

}
