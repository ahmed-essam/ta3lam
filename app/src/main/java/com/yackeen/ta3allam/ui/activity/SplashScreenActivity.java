package com.yackeen.ta3allam.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import com.yackeen.ta3allam.R;
import com.yackeen.ta3allam.util.UserHelper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        int secondsDelay=2;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (UserHelper.getUserId(SplashScreenActivity.this)==null) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, Home.class));
                }
                finish();
            }
        }, secondsDelay * 1000);

    }
}
