package com.example.android.fundamentalscapstone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceFragmentCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager mNotificationManager;
    private static final int BREAKFAST_NOTIFICATION_ID = 0, LUNCH_NOTIFICATION_ID = 1, SUPPER_NOTIFICATION_ID = 2;

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";

    private int notificationIdentifier;


    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
              context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationIdentifier = intent.getIntExtra("intentKey", 0);
        deliverNotification(context);
    }


    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, MainActivity.class);
        if (notificationIdentifier == 0) {
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, BREAKFAST_NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_food)
                    .setContentTitle("Breakfast Time Soon!")
                    .setContentText("You should start preparing for your breakfast meal.")
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(BREAKFAST_NOTIFICATION_ID, builder.build());
        }
        if (notificationIdentifier == 1) {
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, LUNCH_NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_food)
                    .setContentTitle("Lunch Time Soon!")
                    .setContentText("You should start preparing for your lunch meal.")
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(LUNCH_NOTIFICATION_ID, builder.build());
        }
        if (notificationIdentifier == 2) {
            PendingIntent contentPendingIntent = PendingIntent.getActivity
                    (context, SUPPER_NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_food)
                    .setContentTitle("Supper Time Soon!")
                    .setContentText("You should start preparing for your supper meal.")
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(SUPPER_NOTIFICATION_ID, builder.build());
        }


    }

}