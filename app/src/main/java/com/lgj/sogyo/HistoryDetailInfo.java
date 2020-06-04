package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

import java.util.HashMap;
import java.util.Map;

public class HistoryDetailInfo extends AppCompatActivity {
    private RequestQueue queue; //volley가 queue에 response 넣기
    public String url = "http://10.0.2.2:3000/history/location/info"; //보낼 URL.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail_info);
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
            String jsonString = requestJsonObject.toString(); //완성된 json 포맷 여기까진 잘됨.
        } catch (JSONException e) {
            e.printStackTrace();
        }

        array.put(requestJsonObject);

        final RequestQueue requestqueue = Volley.newRequestQueue(HistoryDetailInfo.this);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //핸들러 입력
                try {
                    System.out.println("성공");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String BizName = jsonObject.getString("BizName");
                        System.out.println(BizName);

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