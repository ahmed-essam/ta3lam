package com.yackeen.ta3allam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.drm.DrmStore;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
        loginButton.setReadPermissions("public_profile");
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
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
                Toast.makeText(getApplicationContext(), "Hello " + profile.getName(), Toast.LENGTH_LONG).show();
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
