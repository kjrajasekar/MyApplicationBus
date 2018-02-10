package com.example.nschool.myapplicationbus;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    final int SPLASH_DISPLAY_LENGTH = 1000;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                prefManager = new PrefManager(getApplicationContext());
                if (!prefManager.isFirstTimeLaunch()) {
                    launchHomeScreen();
                    finish();
                }
//                Intent i = new Intent(SplashScreen.this,Main2Activity.class);
//                splash.this.startActivity(i);
//                splash.this.finish();

                Toast.makeText(SplashScreen.this, "home screen", Toast.LENGTH_SHORT).show();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(true);
        Toast.makeText(SplashScreen.this, "register screen", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(splash.this, Main2Activity.class));
        finish();
    }

}
