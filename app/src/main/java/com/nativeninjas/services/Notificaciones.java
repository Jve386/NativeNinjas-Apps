package com.nativeninjas.services;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.nativeninjas.prod1.R;

public class Notificaciones {

    private static final String CHANNEL_ID = "my_channel";
    private static final String CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESCRIPTION = "Channel Description";

    private Context context;

    public Notificaciones(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    public void showNotification(String message) {
        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle("Record superado!")
                .setContentText(message)
                .setSmallIcon(R.drawable.logonativeninjas);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
