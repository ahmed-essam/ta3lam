package yackeen.education.ta3allam.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import yackeen.education.ta3allam.Capsule.Message;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.response.ChatNotificationResponse;
import yackeen.education.ta3allam.model.dto.response.NotificationResponse;
import yackeen.education.ta3allam.model.dto.response.OtherNotificationResponse;
import yackeen.education.ta3allam.ui.activity.Home;
import yackeen.education.ta3allam.ui.activity.MessagesListActivity;

/**
 * Created by ahmed essam on 05/06/2017.
 */

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //create notification
        Log.d(TAG, "onMessageReceived: ");
        String jsonString = remoteMessage.getData().get("body");
        Gson gson = new Gson();

        NotificationResponse notificationResponse = gson.fromJson(jsonString, NotificationResponse.class);
        if (notificationResponse.type == 1 || notificationResponse.type == 2) {
            notificationResponse = new ChatNotificationResponse(notificationResponse);
        } else {
            notificationResponse = new OtherNotificationResponse(notificationResponse);
        }
        Map<String, String> data = remoteMessage.getData();
        for (String s : data.keySet()) {
            Log.d(TAG, s + ": " + data.get(s));
        }
        createNotification(notificationResponse);

        EventBus.getDefault()
                .post(notificationResponse);
    }


    private void createNotification(NotificationResponse notificationResponse) {

        Intent intent = new Intent(MessagingService.this,Home.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("تعلم")
                .setContentText(notificationResponse.getBodyDisplayData())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(12, mNotificationBuilder.build());
    }

}
