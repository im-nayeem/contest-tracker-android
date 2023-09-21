package com.cp.contesttracker.contest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.contesttracker.R;
import com.cp.contesttracker.Utility;
import com.cp.contesttracker.notification.NotificationReceiver;

import java.io.Serializable;

public class ContestDetailsActivity extends AppCompatActivity {
    private TextView contestName;
    private TextView contestTime;
    private TextView contestDuration;
    private TextView contestHost;
    private TextView contestLink;
    private Contest contest;
    private EditText beforeContest;
    private Button reminderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_details);

        final Intent intent = getIntent();
        this.contest = (Contest) intent.getSerializableExtra("contest");
        this.contestName = findViewById(R.id.contest_name);
        this.contestTime = findViewById(R.id.contest_time);
        this.contestHost = findViewById(R.id.contest_host);
        this.contestDuration = findViewById(R.id.contest_duration);
        this.contestLink = findViewById(R.id.contest_link);
        setContestLinkClickListener();

        this.beforeContest = findViewById(R.id.beforeContestTime);
        this.reminderBtn = findViewById(R.id.reminderBtn);
        setReminderBtnClickListener();

        getSupportActionBar().setTitle("Contest Details");

        setData();
    }
    private void setData()
    {
        this.contestName.setText(this.contest.getName());
        this.contestTime.setText(this.contest.getTimeString());
        this.contestDuration.setText(this.contest.getDuration());
        this.contestHost.setText(this.contest.getHost());
        this.contestLink.setText("Click to see details: \n" + this.contest.getContestLink());
    }

    private void setContestLinkClickListener() {
        contestLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contestLink = contest.getContestLink();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contestLink));

                // Check if there is any web browser installed on phone
                if(browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(ContestDetailsActivity.this,
                            "There is no browser installed on your phone!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setReminderBtnClickListener() {
        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long timeOffset = 30 * 60 * 1000;
                String temp = beforeContest.getText().toString();
                if(!temp.equals(""))
                {
                    timeOffset = Long.parseLong(temp) * 60000;
                }
                long contestTimeInMills = Utility.getDateInMills(contest.getTime());
                if(contestTimeInMills == -1 || contestTimeInMills - timeOffset < System.currentTimeMillis() || timeOffset/60000 > 1380)
                {
                    Utility.showDialogueMessage(ContestDetailsActivity.this, "Can't Set Reminder",
                            "Reminder can be set only if the contest is in next 2 days and maximum 23 hours(1380 minutes) before the contest.");
                    return;
                }
                // set the time when the notification to be triggered.
                long futureTimeMillis = contestTimeInMills - timeOffset;

                // create Intent that will be fired when the alarm is triggered.
                Intent notificationIntent = new Intent(ContestDetailsActivity.this, NotificationReceiver.class);
                notificationIntent.putExtra("contest", (Serializable) contest);
                // use unique ID for each notification if needed
                notificationIntent.putExtra("notification_id", 0);

                // create a PendingIntent to wrap the notificationIntent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        ContestDetailsActivity.this,
                        0, // unique request code in case of multiple alarms
                        notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE
                );

                // get the AlarmManager service.
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                // schedule the alarm for the future time.
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        futureTimeMillis,
                        pendingIntent
                );

                String successfullMsg = "Reminder is successfully set for " + timeOffset/60000 +" minutes before the contest.";
                Utility.showDialogueMessage(ContestDetailsActivity.this, "Successfull", successfullMsg);
            }
        });
    }

}
