package yackeen.education.ta3allam.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(yackeen.education.ta3allam.R.layout.activity_splash_screen);

        int secondsDelay=2;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));


                finish();
            }
        }, secondsDelay * 1000);

    }
}
