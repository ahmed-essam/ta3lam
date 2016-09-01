package com.yackeen.ta3allam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yackeen.ta3allam.R;

import java.util.Locale;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad  = "ar"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);
        TextView forgotpassword=(TextView) findViewById(R.id.forgetpassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotpassword =new Intent(Login.this,ForgetPassword.class);
                startActivity(forgotpassword);
            }
        });
        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home=new Intent(Login.this,Home.class);
                startActivity(home);
            }
        });
        TextView register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register=new Intent(Login.this,Register.class);
                startActivity(register);
            }
        });
        Button fblogin=(Button) findViewById(R.id.fblogin);
        fblogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fblogin=new Intent(Login.this,FBLogin.class);
                startActivity(fblogin);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
