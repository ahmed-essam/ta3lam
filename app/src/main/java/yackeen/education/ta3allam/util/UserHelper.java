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
    public static final String USER_TYPE="user_type";
    public static final String security_token="security_token";
    public static final String Photo_url="photo_url";
    public static final String User_name="user_name";





    //User State
    public static boolean isLoggedIn(Context context){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(LOGIN, false);
    }
    public static void saveIntInSharedPreferences(String tag, int value){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().putInt(tag, value).apply();
    }
    public static void saveStringInSharedPreferences(String tag, String value){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().putString(tag,value).apply();
    }
    public static void removeFromSharedPreferences(String tag){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        prefs.edit().remove(tag).apply();
    }
    public static String getUserId(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(USER_ID,null);
    }
    public static int getUserType(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(USER_TYPE,1);
    }
    public static String getSecurityToken(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(security_token,null);
    }
    public static String getPhotoUrl(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Photo_url,null);
    }
    public static String getUserName(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(User_name,null);
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
