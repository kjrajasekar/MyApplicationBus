package com.example.nschool.myapplicationbus;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

/**
 * Activity for show navigated page from Login/Registration activity
 * after successful login/registration
 *
 **/
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toast.makeText(SplashActivity.this, "welcome", Toast.LENGTH_SHORT).show();
    }
}
