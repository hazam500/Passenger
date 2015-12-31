package com.example.toshiba.origin;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.toshiba.passenger.R;

/**
 * Created by TOSHIBA on 26/12/2015.
 */
public class Notificator {

    public void createBusNotification(String bus, Context myContext) {

        long[] pattern = {1000,1000,2000,1000};
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(myContext)
                        .setSmallIcon(R.drawable.ic_action_puzzle)
                        .setContentTitle("Bus hallado!")
                        .setPriority(2)
                        .setVibrate(pattern)
                        .setSound(uri)
                        .setContentText("Su bus es" + bus);

        Intent notificationIntent = new Intent(myContext, OriginActivity.class);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(myContext, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(pendingNotificationIntent);

        int notificationID = 1;
        NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(OriginActivity.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, mBuilder.build());
    }
}
