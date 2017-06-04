package com.yackeen.ta3allam.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.yackeen.ta3allam.application.AppController;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;



public class UserHelper extends FirebaseInstanceIdService {

    private static AppController appContext = AppController.getInstance();
    private static final String TOKEN = "deviceToken";
    private static ProgressDialog dialog;
    public static final String LOGIN = "isLoggedIn";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        prefs.edit().putString(TOKEN, deviceToken).apply();
        Log.d("deviceToken", deviceToken);
    }
    public static String getDeviceToken(Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(TOKEN, "");
    }

    //User State
    public static boolean isLoggedIn(Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(LOGIN, false);
    }
    public static void saveInSharedPreferences(String tag, boolean value){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().putBoolean(tag, value).apply();
    }
    //UI
    public static void showProgressDialog(Context context, String title, String message){

        dialog = ProgressDialog.show(context, title, message, true);
    }
    public static void dismissProgressDialog(){

        if (dialog != null){

            dialog.dismiss();
        }
    }
}
