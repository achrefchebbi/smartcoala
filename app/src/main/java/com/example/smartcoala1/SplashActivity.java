package com.example.smartcoala1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 1000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash screen delay
                Intent intent = new Intent(SplashActivity.this, LoginActivity2.class);
                startActivity(intent);

                // Finish the splash activity to prevent the user from returning to it using the back button
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
