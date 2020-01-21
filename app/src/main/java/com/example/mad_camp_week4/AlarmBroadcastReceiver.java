package com.example.mad_camp_week4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getBooleanExtra("alarm", false)){
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notificationManager.createNotificationChannel(new NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT));
                builder = new NotificationCompat.Builder(context, "CHANNEL_ID");
            }else{
                builder = new NotificationCompat.Builder(context);
            }
            builder.setContentTitle("약먹자");
            builder.setContentText("약먹자");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            Notification notification = builder.build();
            notificationManager.notify(1, notification);
        }
    }
}
