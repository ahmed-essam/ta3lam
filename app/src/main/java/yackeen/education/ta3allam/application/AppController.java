package yackeen.education.ta3allam.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.icu.util.TimeUnit;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.util.TimeUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import yackeen.education.ta3allam.util.LruBitmapCache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppController extends MultiDexApplication {

    //Tags
    public final String TAG = AppController.class.getSimpleName();

    //Instance
    private static AppController instance;

    //Data
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        FlowManager.init(new FlowConfig.Builder(this).build());

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        try {
            String strDate = "2017-07-03T13:44:37.57";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
            Date date = dateFormat.parse(strDate);

            DateFormat targetDateFormate = new SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault());
            Log.d("DATEDATE", targetDateFormate.format(date));

            Log.d("DATEDATE", DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.DAY_IN_MILLIS)+"");
        } catch (ParseException e) {
            Log.d("DATEDATE", e.getMessage());
            e.printStackTrace();
        }




        //printMyHashKey(this);
    }

    //multidexing
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static synchronized AppController getInstance() {

        return instance;
    }

    //Data
    public <T> void addToRequestQueue(Request<T> request) {

        request.setTag(TAG);
        requestQueue.add(request);
    }

    public void cancelPendingRequests() {

        requestQueue.cancelAll(TAG);
    }

    public ImageLoader getImageLoader() {

        if (imageLoader == null) {

            imageLoader = new ImageLoader(this.requestQueue,
                    new LruBitmapCache());
        }
        return this.imageLoader;
    }

    //HashKey generator
    private void printMyHashKey(Context context) {

        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.yackeen.ta3allam",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("MyHashKeyIs:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
    }
}
