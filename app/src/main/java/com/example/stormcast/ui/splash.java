package com.example.stormcast.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.example.stormcast.MainActivity;
import com.example.stormcast.R;


public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            Intent splash = new Intent(splash.this, MainActivity.class);
            @Override
            public void run() {
                startActivity(splash);

                finish();
            }
        },500);


    }
}