package com.yackeen.ta3allam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
}
