package com.yackeen.ta3allam.provider;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.yackeen.ta3allam.ui.Fragment.FacebookLoginFragment;

import java.util.Arrays;


public class AuthFacebook {

    /**
     * This class is responsible for handling the process of signing in or signing up by Facebook and
     * getting any information about the user
     */
    private static CallbackManager mCallbackManager;

    /**
     *  login takes two parameters "Fragment" and "Callback"
     *  and responsible for committing login by facebook
     */
    public static void login(Fragment fragment, FacebookCallback<LoginResult> loginResultFacebookCallback){

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, loginResultFacebookCallback);

        LoginManager.getInstance().logInWithReadPermissions(fragment, Arrays.asList("public_profile", "email"));
    }

    /**
     * onActivityResultHandler is responsible for handling the activity results callback
     */
    public static void onActivityResult(int requestCode, int resultCode, Intent data){

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
