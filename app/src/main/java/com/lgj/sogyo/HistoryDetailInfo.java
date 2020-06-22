package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class HistoryDetailInfo extends Activity {
    private RequestQueue queue; //volley가 queue에 response 넣기
    public String url = "http://ec2-18-188-97-32.us-east-2.compute.amazonaws.com:3000/history/location/info"; //보낼 URL.
    // RecyclerView
    public ArrayList<Store> storeArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_history_detail_info);

        Button button = findViewById(R.id.go_back);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

//
//전 Activity로부터 값을 받아오기
        Intent intent = getIntent();
        double latitude = intent.getExtras().getDouble("latitude");
        double longitude = intent.getExtras().getDouble("longitude");
        final String latitude_s = Double.toString(latitude);
        final String longitude_s = Double.toString(longitude);

//Volley Network. 새롭게 짬.
        JSONArray array = new JSONArray();
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("longitude", longitude_s);
            requestJsonObject.put("latitude", latitude_s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        array.put(requestJsonObject);

//RECYCLERVIEW 구현
        //리사이클러뷰에 Linearlayoutmanager 객체 지정
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //리사이클러뷰에 simpleTextadapter 지정
        final SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter(storeArrayList);
        recyclerView.setAdapter(simpleTextAdapter);

//Volley 구성
        final RequestQueue requestqueue = Volley.newRequestQueue(HistoryDetailInfo.this);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //핸들러 입력
                try {
//                    System.out.println("성공");
                    for (int i = 0; i < response.length(); i++) {
                        //불러오는 과정.
                        JSONObject jsonObject = response.getJSONObject(i);
                        String BizName = jsonObject.getString("BizName");
                        String upperCategory = jsonObject.getString("upperCategory");
                        String floor = jsonObject.getString("floor");
                        int IsOpen = jsonObject.getInt("IsOpen");
                        String openYear = jsonObject.getString("openYear").substring(0,7);
                        String closeYear = jsonObject.getString("closeYear");
                        if(floor.contains("null")){floor="1";}
                        String c = "층";
                        floor = floor.concat(c); //문자열 붙이기
                        String Isopenstr="";
                        if(IsOpen==1){
                            Isopenstr= "운영중";
                        }
                        else{
                            Isopenstr="폐점";
                        }
                        if(closeYear!="null"){ closeYear=closeYear.substring(0,7); }
                        else{ closeYear = " "; }
                        ////////////////////////////
                        Store store = new Store(BizName,upperCategory,floor,Isopenstr,openYear,closeYear);
                        storeArrayList.add(store);
                        simpleTextAdapter.notifyDataSetChanged();

                        System.out.println("상점정보 : " + BizName +" "+ upperCategory +" " + floor +" " +Isopenstr +" "+openYear +" "+closeYear);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestqueue.add(jsonArrayRequest);

    }
}