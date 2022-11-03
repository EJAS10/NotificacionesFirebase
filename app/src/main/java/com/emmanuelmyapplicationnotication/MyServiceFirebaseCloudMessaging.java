package com.emmanuelmyapplicationnotication;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyServiceFirebaseCloudMessaging extends FirebaseMessagingService {

    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
    NotificationCompat.Builder mBuilder;
    public MyServiceFirebaseCloudMessaging() {
        //FirebaseApp.initializeApp(getApplicationContext());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // ...
        createNotificationChannel();
        createNotification(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("TAG", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d("TAG", "Message data payload: " + data.get("datos"));
            Log.d("TAG", "Message data payload: " + data.get("other"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    //}
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(RemoteMessage remoteMessage){

        String title = remoteMessage.getNotification().getTitle();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle(title);
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setColor(getResources().getColor(R.color.blue));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
    /*public void setNotification(RemoteMessage remoteMessage){
        String channelId = "canal de alertas";
        String nameNotification = "Alertas";
        String descriptionChannel = "Alertas pendiente";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        //NotificationManager mNotificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        NotificationManager mNotificationManager = getSystemService(NotificationManager.class);
        mBuilder = new NotificationCompat.Builder(this, null);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(channelId, nameNotification, importance);
            mChannel.setDescription(descriptionChannel);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            mNotificationManager.createNotificationChannel(mChannel);

            mBuilder = new NotificationCompat.Builder(this, channelId);
        }
        NotificationCompat.InboxStyle notificationText = new NotificationCompat.InboxStyle();

        Intent notificationIntent = new Intent(this, MainActivity.class);

        notificationIntent.putExtra("provandow",0);

        PendingIntent contentIntent = PendingIntent.getActivity(MyServiceFirebaseCloudMessaging.this, 2,notificationIntent,0);

        mBuilder.setLargeIcon(
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("probandoemma")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Emmanuel")
                .setContentText("description")
                .setContentIntent(contentIntent).setAutoCancel(true);
        mBuilder.setStyle(notificationText);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notification.ledARGB = Color.RED;
        notification.ledOnMS = 2000;
        notification.ledOffMS = 500;

        mNotificationManager.notify(23, notification);
    }*/
}