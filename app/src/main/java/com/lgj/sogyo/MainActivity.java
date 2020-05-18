package com.lgj.sogyo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Sogyo");
        setSupportActionBar(toolbar);

        Button button_history = (Button)findViewById(R.id.button_history);
        Button button_commercialAnalyze=(Button)findViewById(R.id.button_commercialAnalyze);
        Button button_judgement=(Button)findViewById(R.id.button_judgementActivity);
        button_history.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
        button_commercialAnalyze.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CommercialAnalyze.class);
                startActivity(intent);
            }
        });
        button_judgement.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,judgementActivity.class);
                startActivity(intent);
            }
        });
    }
//
}
