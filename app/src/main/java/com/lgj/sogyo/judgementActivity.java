package com.lgj.sogyo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import org.json.JSONException;
import org.json.JSONObject;


public class judgementActivity extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout  drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Spinner spinner;
    TextView textView;
    Button btn;
    CheckBox checkBox ;
    EditText editText;
    RequestQueue queue;
    String selectedspinner;
    String url = "http://10.0.2.2:3000/judgement";
    Bundle bundle = new Bundle(); //fragment 전송시켜줄 객체 선언
    Double judge_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judgement);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Sogyo");
//volley
//        queue= Volley.newRequestQueue(this);
        //spinner.setPrompt("Select Category");
        setSupportActionBar(toolbar);
        navigationView=findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);// 사이드 메뉴에 아이콘 색깔을 원래 아이콘 색으로

        drawerLayout=findViewById(R.id.layout_drawer);
        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);//누를때마다 아이콘이 팽그르 돈다.
        drawerToggle.syncState();// 삼선 메뉴 추가

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_home:
                        Intent intent = new Intent(judgementActivity.this,MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_Commercial_Analyze:
                        intent = new Intent(judgementActivity.this,CommercialAnalyze_main.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_history:
                        intent = new Intent(judgementActivity.this,HistoryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_judgement:
                        intent = new Intent(judgementActivity.this,judgementActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });
        // 선택된 카테고리, 프렌차이즈체크여부, 가격정보를 서버로 전송해야한다.
        textView=(TextView)findViewById(R.id.tv_view);
        spinner=(Spinner)findViewById(R.id.spinner);
        checkBox=(CheckBox)findViewById(R.id.checked);
        editText=(EditText)findViewById(R.id.cost);
        btn=(Button)findViewById(R.id.jud_btn);
        //ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Category,android.R.layout.simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdeapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                selectedspinner=parent.getItemAtPosition(pos).toString();
            }
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//Volley Network
                JSONObject requestJsonObject = new JSONObject(); //2
                try {
                    //System.out.println("서버 넣기 전");
                    requestJsonObject.put("category", selectedspinner);
                    if(checkBox.isChecked()){
                        requestJsonObject.put("cost",editText.getText().toString() );
                    }
                    System.out.println("객체 확인"+requestJsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final RequestQueue requestqueue = Volley.newRequestQueue(judgementActivity.this);
                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestJsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("성공");
                            Double a = response.getDouble("judge_score");
                            judge_score = a;
                            System.out.println(a);
                        }
                        catch(JSONException e){
                            e.printStackTrace(); } }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) { } });

                requestqueue.add(jsonObjectRequest);

//               Intent intent= new Intent(getApplicationContext(),Judgement_Result.class);
//                startActivity(intent);

//Fragement 띄우기 구현

                Handler mHandler = new Handler();
                Runnable mMyTask = new Runnable() {
                    @Override
                    public void run() {
                        // 실제 동작
                        //                    bundle.putString("category",selectedspinner); //프래그먼트에 보낼 데이터
                        //                    bundle.putString("cost",editText.getText().toString()); //프래그먼트에 보낼 데이터
                        //                    bundle.putBoolean("checkBox",checkBox.isChecked());
                        Intent myIntent = new Intent(judgementActivity.this, judgeResult.class);
                        boolean a = checkBox.isChecked();
                        String b = editText.getText().toString();
                        String c = selectedspinner;
                        myIntent.putExtra("checkBox",a);
                        myIntent.putExtra("cost",b);
                        myIntent.putExtra("category",c);
                        myIntent.putExtra("judge_score",judge_score);
                        startActivity(myIntent);

//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        JudgeFragment judgeFragment = new JudgeFragment();
//                        judgeFragment.setArguments(bundle); //보낼 데이터 지정

                        //로딩화면 표시
//                        transaction.replace(R.id.frame,judgeFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                        btn.setVisibility(View.INVISIBLE);
                    }
                };
                mHandler.postDelayed(mMyTask, 1500); // 1.5초후에 실행

            }
        });


//        queue.add();
    }
//
}