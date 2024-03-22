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
import com.cp.contesttracker.database.DatabaseHelper;
import com.cp.contesttracker.database.DatabaseQuery;

import java.io.Serializable;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the notification ID from the intent
        int notificationId = intent.getIntExtra("notification_id", Utility.getUniqueId());
        Contest contest =  (Contest) intent.getSerializableExtra("contest");

        int timeOffset = (int) intent.getLongExtra("timeOffset", 30);
        DatabaseQuery databaseQuery = new DatabaseQuery(context);
        databaseQuery.deleteNotificationSchedule(contest.getId(), timeOffset);

        // Build and show the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(contest.getName())
                .setContentText(contest.getTimeString())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

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
