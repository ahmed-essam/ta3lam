package yackeen.education.ta3allam.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import yackeen.education.ta3allam.application.AppController;


public class UserHelper {

    private static AppController appContext = AppController.getInstance();
    private static ProgressDialog dialog;
    public static final String LOGIN = "isLoggedIn";
    public static final String USER_ID="user_id";

    //User State
    public static boolean isLoggedIn(Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(LOGIN, false);
    }
    public static void saveInSharedPreferences(String tag, boolean value){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().putBoolean(tag, value).apply();
    }
    public static void saveStringInSharedPreferences(String tag, String value){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().putString(USER_ID,value).apply();
    }
    public static String getUserId(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(USER_ID,null);
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