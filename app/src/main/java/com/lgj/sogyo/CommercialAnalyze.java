package com.lgj.sogyo;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
public class CommercialAnalyze extends AppCompatActivity {

    NavigationView navigationView;
    DrawerLayout  drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_analyze);
            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            toolbar.setTitle("Sogyo");

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
                            Intent intent = new Intent(CommercialAnalyze.this,MainActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.menu_Commercial_Analyze:
                            intent = new Intent(CommercialAnalyze.this,CommercialAnalyze.class);
                            startActivity(intent);
                            return true;
                        case R.id.menu_history:
                            intent = new Intent(CommercialAnalyze.this,HistoryActivity.class);
                            startActivity(intent);
                            return true;
                        case R.id.menu_judgement:
                            intent = new Intent(CommercialAnalyze.this,judgementActivity.class);
                            startActivity(intent);
                            return true;
                    }
                    return false;
                }
            });
    }
//
}
