package com.lgj.sogyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_example);
        tv = findViewById(R.id.tvMain);
        queue = Volley.newRequestQueue(this); //큐 초기화
        String url="http://10.0.2.2:3000/history/location"; //요청 보낼 url 현재 지금 있는건 임의로 만든 거임.

        //String 형식으로 request를 받아옴, 이거는 json 형식으로도 받아올 수 있는데,
        //json 형식으로 받아오면 무조건적으로 어플이 꺼짐. 이유 모름. 그 이유 찾는게 한 세월임. 이거 알고 끝나고 찾아봐야함.

        //request/ repsonse 하는 방식은 거의 모두 이렇게 구성이 됨.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //여기에서 받아온 response에 대해서 처리를 어떻게 할 건지 적어주면 됨.
                //현재는 그냥 텍스트 뷰에 보여주기만 해서, 이렇게 써둠.
                tv.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error 처리해주는 함수.
            }
        });

        stringRequest.setTag(TAG);
        queue.add(stringRequest); //큐에 추가를 해줌. request를 추가해주는 과정임.
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue!=null){
            queue.cancelAll(TAG);
        }
    }
}
