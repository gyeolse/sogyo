package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class judgeResult extends AppCompatActivity {

    private RequestQueue queue; //volley가 queue에 response 넣기
    public String url = "http://ec2-18-188-97-32.us-east-2.compute.amazonaws.com:3000/judgement/result"; //보낼 URL.
    //Intent
    public boolean isCorrect; //적합_부적합 판정
    public double judge_score;
    // RecyclerView
    public ArrayList<Francise_item> rvViewDatalist = new ArrayList<>();
    public FranciseAdapter franciseAdapter;
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager mLayoutManager;
    TextView eligible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_result);
        Button button = findViewById(R.id.go_back);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eligible = findViewById(R.id.eligible);

        Intent intent = getIntent();
        judge_score = intent.getExtras().getDouble("judge_score");
        final String cost = intent.getExtras().getString("cost");
        final String category = intent.getExtras().getString("category");
        final boolean checkBox = intent.getExtras().getBoolean("checkBox");
        if(checkBox == false){
            TextView francise_list_tv = findViewById(R.id.francise_list_tv);
            francise_list_tv.setVisibility(View.INVISIBLE);
        }
        System.out.println(checkBox);
        System.out.println(cost);
        System.out.println(category);

        if (judge_score > 2.5) {
            isCorrect = true; //적합
            eligible.setText(category+"은(는) 이 상권에 적합합니다.");
        } else {
            isCorrect = false; //부적합
            eligible.setText(category+"은(는) 이 상권에 부적합합니다.");

        }
        if (checkBox == true) { //CheckBox True일때만 실행

            JSONArray array = new JSONArray();
            JSONObject requestJsonObject = new JSONObject(); //Json object
            try { //예외 처리 JSON 처리할떄 -> 오류 -> JSONEXCEPTION
                System.out.println("request 시도");
                requestJsonObject.put("category", category); //json 형식으로 보내줘야함.
                if (cost != null) {
                    requestJsonObject.put("cost", cost); //json 형식으로 보내줘야함.
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(requestJsonObject);
//RECYCLERVIEW 구현
            //리사이클러뷰에 Linearlayoutmanager 객체 지정
            RecyclerView recyclerView = findViewById(R.id.francise_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //리사이클러뷰에 simpleTextadapter 지정
            final FranciseAdapter franciseAdapter = new FranciseAdapter(rvViewDatalist);
            recyclerView.setAdapter(franciseAdapter);

            //Volley Response. JSON array response
            RequestQueue queue = Volley.newRequestQueue(this); //volley가 queue에 response 넣기
            final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, array, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //핸들러 입력
                    try {
                        System.out.println("성공");
                        for (int i = 0; i < response.length(); i++) {
                            //불러오는 과정.
                            JSONObject jsonObject = response.getJSONObject(i);
// lowerCategory,fran_name,total_money
                            String lowerCategory = jsonObject.getString("lowerCategory");
                            String fran_name = jsonObject.getString("fran_name");
                            String total_money = jsonObject.getString("total_money");
                            if (total_money == "") {
                                total_money = "0";
                            }
                            if (fran_name == "") {
                                fran_name = "X";
                            }
                            Francise_item rvitem = new Francise_item(lowerCategory, fran_name, total_money);
                            rvViewDatalist.add(rvitem);
                            franciseAdapter.notifyDataSetChanged();
                            System.out.println("프랜차이즈 정보 : " + fran_name + " && " + cost);

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
            queue.add(jsonArrayRequest);
        }

    }
}
