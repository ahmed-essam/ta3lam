package yackeen.education.ta3allam.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ahmed essam on 05/06/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TOKEN = "deviceToken";
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
}
