package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//volley 라이브러리 쓰기 전에 dependency도 추가해주고, manifest에서 user permission internet 추가해줘야함.
//지금은 추가되어있는 상태
//NODE JS 로 포트 3000번 열어주고, 실행해야함
//번외로, json 형식 처리하기 위해서, store 클래스 생성해둠.
//전에 했던 과정이 뭐냐면 json으로 받아오고, 그걸 store 클래스에 데이터를 넣어주고, store들로 이루어진
//store List를 생성하고자 했음. 근데 그렇게 받아오면 앱이 뻗어버리는 오류가 났음. 일단 지금은 받아오기까지 되어있는 상태

public class VolleyExampleActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    private TextView tv;
    private RequestQueue queue; //volley가 queue에 response를 넣어주고, 그거를 차례때로 뽑아서 서버에 보내는 구조임

    public int StoreNo;
    public String BizName;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_example);
        tv = findViewById(R.id.tvMain);
        queue = Volley.newRequestQueue(this); //큐 초기화

        String url = "http://10.0.2.2:3000/history/location"; //요청 보낼 url 현재 지금 있는건 임의로 만든 거임.

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int StoreNo = jsonObject.getInt("StoreNo");
                        String BizName = jsonObject.getString("BizName");

                        tv.append(StoreNo + BizName+"\n");
//                        tv.setText(StoreNo + BizName);
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
    }

 */
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_volley_example);
    tv = findViewById(R.id.tvMain);
    queue = Volley.newRequestQueue(this);
    String url="http://ec2-18-188-97-32.us-east-2.compute.amazonaws.com:3000/judgement";

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            tv.setText(response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });

    stringRequest.setTag(TAG);
    queue.add(stringRequest);
}
}

