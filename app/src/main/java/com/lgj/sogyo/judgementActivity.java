package com.lgj.sogyo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

                JSONArray array = new JSONArray(); //1
                JSONObject requestJsonObject = new JSONObject(); //2
                try {
                    //System.out.println("서버 넣기 전");
                    requestJsonObject.put("category", selectedspinner);
                    if(checkBox.isChecked()){
                        requestJsonObject.put("cost",editText.getText().toString() );
                    }
                    array.put(requestJsonObject);
                    System.out.println("객체 확인"+requestJsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                final RequestQueue requestqueue = Volley.newRequestQueue(judgementActivity.this);
                final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, array, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //핸들러 입력
                        try {
                            System.out.println("성공");
                            JSONObject jsonObject = response.getJSONObject(0);
                            System.out.println(jsonObject);
                            //System.out.println(response.length());

                        } catch(JSONException e) {
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

//               Intent intent= new Intent(getApplicationContext(),Judgement_Result.class);
//                startActivity(intent);
            }
        });


//        queue.add();
    }
//
}