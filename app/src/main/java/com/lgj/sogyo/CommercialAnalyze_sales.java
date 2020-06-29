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
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
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
//import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;
import android.graphics.Color;
import android.widget.Toast;

public class CommercialAnalyze_sales extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ListView listview;
    ArrayAdapter Adapter;
    private static final String TAG = "MAIN";
    LineChart chart;
    LineData chartData;
    RequestQueue queue;
    String[] lb = new String[]{"커피전문점/카페/다방", "패스트푸드", "한식/백반/한정식", "국수/만두/칼국수", "후라이드/양념치킨", "곱창/양구이전문",
            "라면김밥분식", "중국음식/중국집", "동남아음식", "제과점", "도시락전문","양식", "유흥주점", "피자전문", "죽전문점", "일식수산물", "족발/보쌈전문", "아이스크림판매",
            "떡볶이전문", "갈비/삼겹살", "닭/오리요리"};
    ArrayList<Integer>num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_analyze_sales);
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
                        Intent intent = new Intent(CommercialAnalyze_sales.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(CommercialAnalyze_sales.this, CommercialAnalyze_main.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(CommercialAnalyze_sales.this, HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(CommercialAnalyze_sales.this, judgementActivity.class);
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
        final myMarkerView mv=new myMarkerView(this,R.layout.activity_my_marker_view);
        apply.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {

                SparseBooleanArray checkedItems = listview.getCheckedItemPositions();
                int count = Adapter.getCount();
                int cnt1 = 0;
                for (int i = 0; i < 21; i++) {
                    if (checkedItems.get(i))
                        cnt1++;
                }
                if(cnt1==0)
                    Toast.makeText(CommercialAnalyze_sales.this, "1개 이상 선택하십시오", Toast.LENGTH_SHORT).show();
                else if (cnt1 > 4)
                    Toast.makeText(CommercialAnalyze_sales.this, "4개까지만 선택하십시오", Toast.LENGTH_SHORT).show();
                else {
                    chart = findViewById(R.id.chart);
                    chartData = new LineData();
                    String SQL = "select lowerCategory, qt_sales, quarter from sales where";
                    int cnt = 0;
                    num = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        if (checkedItems.get(i)) {
                            if (cnt == 0) {
                                String s = " lowerCategory='" + lb[i] + "'";
                                SQL += s;
                                cnt++;
                            } else {
                                String s = " or lowerCategory='" + lb[i] + "'";
                                SQL += s;
                            }
                            num.add(i);
                        }
                    }
                    SQL += ";";
                    JSONObject requestJsonObject = new JSONObject(); //2
                    try {
                        //System.out.println("서버 넣기 전");
                        requestJsonObject.put("Q", SQL);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray j = new JSONArray();
                    j.put(requestJsonObject);

                    String url = "http://ec2-18-188-97-32.us-east-2.compute.amazonaws.com:3000/CommercialAnalyze/total";

                    final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                            Request.Method.POST, url, j, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                System.out.println(response.length());
                                for (int i = 0; i < response.length(); i += 4) {
                                    ArrayList<Entry> arr = new ArrayList<>();
                                    String Label = "";
                                    for (int t = 0; t < 4; t++) {
                                        JSONObject jsonObject = response.getJSONObject(i + t);
                                        if (t % 4 == 0) {
                                            Label = jsonObject.getString("lowerCategory");
                                        }
                                        int quarter = jsonObject.getInt("quarter");
                                        int qt_sales = jsonObject.getInt("qt_sales");
                                        arr.add(new Entry(Float.valueOf(quarter), Float.valueOf(qt_sales)));
                                    }
                                    LineDataSet set = new LineDataSet(arr, Label);
                                    Random rand = new Random();
                                    int c = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
                                    set.setColor(c);
                                    set.setCircleColors(c);
                                    set.setLineWidth(2);
                                    set.setValueTextSize(15);
                                    chartData.addDataSet(set);
                                }

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

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chartData.setDrawValues(false);
                            chart.setMarkerView(mv);
                            chart.setDrawMarkerViews(true);
                            chart.setBackgroundColor(Color.WHITE);
                            chart.setData(chartData);
                            chart.setDrawBorders(true);
                            chart.setBorderWidth(2);
                            XAxis xAxis = chart.getXAxis();
                            xAxis.setLabelCount(3);
                            YAxis yLAxis = chart.getAxisLeft();
                            yLAxis.setAxisMaximum(8000);
                            yLAxis.setAxisMinimum(2000);
                            yLAxis.setLabelCount(4);
                            YAxis yRAxis = chart.getAxisRight();
                            yRAxis.setAxisMaximum(8000);
                            yRAxis.setAxisMinimum(2000);
                            yRAxis.setLabelCount(4);
                            Legend legend = chart.getLegend();
                            legend.setEnabled(true);
                            legend.setTextColor(Color.RED);
                            chart.invalidate();
                        }
                    }, 800);
                }
            }
        });


    }

}