package com.nishan.tasker.Notification;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.nishan.tasker.MainActivity;
import com.nishan.tasker.R;

/**
 * Created by NaRan on 8/17/17.
 */

public class NotificationUtils {

    public static void scheduleNotification(Context context, String title, String body, int delay){

        int notification_id = 1;

        Notification notification = generateNotification(context, title, body);

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notification_id);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        long futureMilli = SystemClock.elapsedRealtime() + delay;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,futureMilli,pendingIntent);


        // To cancel an alarm - pass the pending intent to cancel
        //alarmManager.cancel(pendingIntent);

    }

    public static Notification generateNotification(Context context, String title, String body) {

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        return new NotificationCompat.Builder(context)
                    .setContentTitle(title).setContentText(body)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();


    }

}
