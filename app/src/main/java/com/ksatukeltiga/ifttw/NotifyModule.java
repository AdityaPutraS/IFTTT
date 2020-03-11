package com.ksatukeltiga.ifttw;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyModule extends ActionModule {
    private String notification;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Context context;

    public NotifyModule(String notification, Context context)
    {
        this.moduleName = "NotifyModule";
        this.data = notification;
        this.notification = notification;
        this.actionString = "Notify \"" + notification + "\"";
        this.context = context;

        // Buat Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_id = "3000";
            CharSequence name = "NotifyModule";
            String description = "Channel untuk notify module ifttw";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(description);
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(context, channel_id);
        } else {
            Log.println(Log.INFO, "NotifyModule", "wow tua");
            builder = new NotificationCompat.Builder(context);
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Notification")
            .setContentText(notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        this.notification = data;
    }

    @Override
    public void doAction()
    {
        notificationManager.notify(1, builder.build());
        Log.println(Log.DEBUG, "action", "notify harusnya");
    }
}
