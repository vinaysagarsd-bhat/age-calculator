package com.agecalculator.splashscreen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.agecalculator.dashboard.MainActivity;
import com.agecalculator.R;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent dashboardIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(dashboardIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}