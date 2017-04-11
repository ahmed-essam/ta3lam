package com.yackeen.ta3allam.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import static com.yackeen.ta3allam.util.Constants.LOGIN;

public class UserHelper extends FirebaseInstanceIdService {

    private String deviceToken;
    private static ProgressDialog dialog;
    private static UserHelper instance = new UserHelper();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("deviceToken", deviceToken);
    }
    public String getDeviceToken(){

        return deviceToken;
    }
    public static UserHelper getInstance(){

        return instance;
    }

    //State
    public boolean isLoggedIn(Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(LOGIN, false);
    }
    public void setLoggedIn(Context context, boolean state){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(LOGIN, state).apply();
    }

    //UI
    public void showProgressDialog(Context context, String title, String message){

        dialog = ProgressDialog.show(context, title, message, true);
    }
    public void dismissProgressDialog(){

        if (dialog != null){

            dialog.dismiss();
        }
    }
}
