package com.yackeen.ta3allam.Activity;

import android.content.Intent;

import android.content.res.Configuration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.yackeen.ta3allam.R;


import java.util.Arrays;
import java.util.Locale;

public class FBLogin extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    Profile profile;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad = "ar"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_fblogin);
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getApplication());

        loginButton = (LoginButton) findViewById(R.id.fblogin);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                        Profile.setCurrentProfile(currentProfile);

                    }
                };
                profileTracker.startTracking();

                profile = Profile.getCurrentProfile();
                Toast.makeText(FBLogin.this,profile.getId()+"",Toast.LENGTH_LONG).show();
                Intent home=new Intent(FBLogin.this,Home.class);
                startActivity(home);
            }

            @Override
            public void onCancel() {
                Intent login = new Intent(FBLogin.this, Login.class);
                startActivity(login);
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                Intent login = new Intent(FBLogin.this, Login.class);
                startActivity(login);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
