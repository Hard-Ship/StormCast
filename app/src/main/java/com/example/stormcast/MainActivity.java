package com.example.stormcast;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.stormcast.ui.Fragment_forecast;
import com.example.stormcast.ui.Fragment_weather;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frame ;
    BottomNavigationView btnav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame = findViewById(R.id.frame);
        btnav = findViewById(R.id.btnav);

        //default fragment
        loadfrag(new Fragment_weather(),0);

        btnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id==R.id.nav_weather){
                    loadfrag(new Fragment_weather(),1);
                }
                else if (id==R.id.nav_forecast) {
                    loadfrag(new Fragment_forecast(),1);
                }

                return true;
            }
        });




    }

    public void loadfrag(Fragment fragment,int flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag==0) {
            ft.add(R.id.frame, fragment);
        }
        else {
            ft.replace(R.id.frame, fragment);
        }
        ft.commit();
    }
}