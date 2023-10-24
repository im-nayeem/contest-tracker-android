package com.cp.contesttracker.contest;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.cp.contesttracker.Utility;
import com.cp.contesttracker.database.DatabaseQuery;
import com.cp.contesttracker.notification.NotificationReceiver;

import java.io.Serializable;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class ReminderNotification implements View.OnClickListener {

    private Contest contest;
    private EditText timesAhead;
    private Context context;
    private DatabaseQuery databaseQuery;
    private NotificationCallback notificationCallback;

    public ReminderNotification(Context context, Contest contest, EditText timesAhead, NotificationCallback notificationCallback) {
        this.context = context;
        this.contest = contest;
        this.timesAhead = timesAhead;
        this.databaseQuery = new DatabaseQuery(this.context);
        this.notificationCallback = notificationCallback;
    }

    @Override
    public void onClick(View v) {

        long timeOffset = 30;
        String minutesAhead = timesAhead.getText().toString();
        if(!minutesAhead.equals(""))
        {
            timeOffset = Long.parseLong(minutesAhead);
        }
        List<String> list = databaseQuery.getAllSchedule(contest.getId());
        if(list.contains(timeOffset + " minutes"))
        {
            Utility.showDialogueMessage(context, "Duplicate Reminder", "Reminder notification is already set for " +
                    timeOffset + " minutes before the contest");
            return;
        }
        timeOffset *= 60000;
        long contestTimeInMills = Utility.getDateInMills(contest.getTime());

        if(contestTimeInMills == -1 || contestTimeInMills - timeOffset < System.currentTimeMillis() || timeOffset/60000 > 1380)
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

        databaseQuery.insertNotificationSchedule(contest.getId(), (int) (timeOffset / 60000) );

        int remainingHours = (int) ((futureTimeMillis - System.currentTimeMillis()) / 3600000);
        int remainingMinutes = (int) (((futureTimeMillis - System.currentTimeMillis()) / 60000) % 60);

        String successfullMsg = "Reminder is successfully set for " + timeOffset/60000 +
                " minutes before the contest. \nYou will get reminder after "+ remainingHours +
                " hours and " + remainingMinutes +
                " minutes from now.";
        dialogueMessageWithCallback("Successfull", successfullMsg);
    }


    private void dialogueMessageWithCallback(String title, String details) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(details)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the OK button click if needed
                        dialog.dismiss(); // Close the dialog
                        notificationCallback.onNotificationSet();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
