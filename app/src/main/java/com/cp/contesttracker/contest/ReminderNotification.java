package com.cp.contesttracker.contest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.cp.contesttracker.Utility;
import com.cp.contesttracker.notification.NotificationReceiver;

import java.io.Serializable;

import static android.content.Context.ALARM_SERVICE;

public class ReminderNotification implements View.OnClickListener {

    private Contest contest;
    private EditText timesAhead;
    private Context context;

    public ReminderNotification(Context context, Contest contest, EditText timesAhead) {
        this.context = context;
        this.contest = contest;
        this.timesAhead = timesAhead;
    }
    @Override
    public void onClick(View v) {

//                long qId = databaseQuery.insertNotificationSchedule(contest.getId(), (int) (timeOffset/60000));
//                Log.e("query: ", String.valueOf(qId));
        long timeOffset = 30 * 60 * 1000;
        String minutesAhead = timesAhead.getText().toString();
        if(!minutesAhead.equals(""))
        {
            timeOffset = Long.parseLong(minutesAhead) * 60000;
        }

        long contestTimeInMills = Utility.getDateInMills(contest.getTime());
        if(contestTimeInMills == -1 || contestTimeInMills - timeOffset < System.currentTimeMillis() /*|| timeOffset/60000 > 1380*/)
        {
            Utility.showDialogueMessage(context, "Can't Set Reminder",
                    "Reminder can be set only if the contest is in next 2 days and maximum 23 hours(1380 minutes) before the contest.");
            return;
        }

        // set the time when the notification to be triggered.
        long futureTimeMillis = contestTimeInMills - timeOffset;
        int uniqueId = Utility.getUniqueId();

        // create Intent that will be fired when the alarm is triggered.
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.putExtra("contest", (Serializable) contest);

        // unique ID for each notification
        notificationIntent.putExtra("notification_id", uniqueId);

        // create a PendingIntent to wrap the notificationIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                uniqueId,          // unique request code in case of multiple alarms
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // get the AlarmManager service.
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        // schedule the alarm for the future time.
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                futureTimeMillis,
                pendingIntent
        );

        int remainingHours = (int) ((futureTimeMillis - System.currentTimeMillis()) / 3600000);
        int remainingMinutes = (int) (((futureTimeMillis - System.currentTimeMillis()) / 60000) % 60);
        String successfullMsg = "Reminder is successfully set for " + timeOffset/60000 +
                " minutes before the contest. \nYou will get reminder after "+ remainingHours +
                " hours and " + remainingMinutes +
                " minutes from now.";
        Utility.showDialogueMessage(context, "Successfull", successfullMsg);
    }
}
