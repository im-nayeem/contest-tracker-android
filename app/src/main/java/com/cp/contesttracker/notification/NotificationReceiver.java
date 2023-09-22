package com.cp.contesttracker.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.cp.contesttracker.R;
import com.cp.contesttracker.Utility;
import com.cp.contesttracker.contest.Contest;
import com.cp.contesttracker.contest.ContestDetailsActivity;
import java.io.Serializable;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the notification ID from the intent
        int notificationId = intent.getIntExtra("notification_id", 0);
        Contest contest =  (Contest) intent.getSerializableExtra("contest");

        // Build and show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(contest.getName())
                .setContentText(contest.getTimeString())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Create an explicit intent for an Activity in your app
        // Set what happens on tap to the notification
        Intent responseIntent = new Intent(context, ContestDetailsActivity.class);
        responseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        responseIntent.putExtra("contest", (Serializable)contest);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Utility.getUniqueId() + 1, responseIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Notify
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }
}
