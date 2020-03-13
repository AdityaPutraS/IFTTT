package com.ksatukeltiga.ifttw;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Arrays;
import java.util.Random;

public class NotifyModule extends ActionModule {
    private final String GROUP_KEY = "com.ksatukeltiga.ifttw.NOTIFY_ME";
    private int notificationId;
    private String notification;
    private String title;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    public NotifyModule()
    {
        super(0,
                "",
                "NotifyModule",
                "");
    }
    public NotifyModule(String title, String notification)
    {
        super(0,
                new Random().nextInt() + "---" + title + "---" + notification,
                "NotifyModule",
                "Notify (" + title +  ") \"" + notification + "\"");
        this.setData(this.data);

    }

    @Override
    public void setData(String data)
    {
        this.data = data;
        String[] temp = data.split("---");
        Log.println(Log.INFO, "NotifyModule", "SetData : " + data + " " + Arrays.toString(temp));
        if(temp.length == 3) {
            this.notificationId = Integer.parseInt(temp[0]);
            this.title = temp[1];
            this.notification = temp[2];
        }
        updateActionString();
    }

    @Override
    public void updateActionString() {
        this.actionString = "Notify (" + title +  ") \"" + notification + "\"";
    }

    @Override
    public Bundle getBundle()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("idNotification", this.notificationId);
        bundle.putString("judul", this.title);
        bundle.putString("pesan", this.notification);
        return bundle;
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.println(Log.INFO, "NotifyModule", "Background service notify jalan");
        int idNotif = workIntent.getIntExtra("idNotification", 1);
        String judul = workIntent.getStringExtra("judul");
        String pesan = workIntent.getStringExtra("pesan");
        Log.println(Log.INFO, "NotifyModule", "Dapat notif : " + judul +  "-=-" + pesan);
        // Buat Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_id = "3000";
            CharSequence name = "NotifyModule";
            String description = "Channel untuk notify module ifttw";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channel_id, name, importance);
            channel.setDescription(description);
            notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, channel_id);
        } else {
            Log.println(Log.INFO, "NotifyModule", "wow tua");
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(judul)
                .setContentText(pesan)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setGroup(GROUP_KEY)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(pesan));

        notificationManager.notify(idNotif, builder.build());
        builder.setGroupSummary(true);
        notificationManager.notify(0, builder.build());
        Log.println(Log.DEBUG, "NotifyModule", "notify harusnya");
    }
}
