package com.cp.contesttracker.contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cp.contesttracker.R;
import com.cp.contesttracker.database.DatabaseQuery;

import java.util.List;

public class ContestDetailsActivity extends AppCompatActivity {
    private TextView contestName;
    private TextView contestTime;
    private TextView contestDuration;
    private TextView contestHost;
    private TextView contestLink;
    private Contest contest;
    private EditText beforeContest;
    private Button reminderBtn;
    private DatabaseQuery databaseQuery;

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

        this.databaseQuery = new DatabaseQuery(this);
        setData();
    }
    private void setData()
    {
        this.contestName.setText(this.contest.getName());
        this.contestTime.setText(this.contest.getTimeString());
        this.contestDuration.setText(this.contest.getDuration());
        this.contestHost.setText(this.contest.getHost());
        this.contestLink.setText("Click to see details: \n" + this.contest.getContestLink());
        List<String> list = databaseQuery.getAllSchedule(this.contest.getId());
        if(list.size() != 0)
        {
            TextView notificationListHeader = findViewById(R.id.notfication_list_header);
            notificationListHeader.setText("You've set notifications before: ");
            ListView listView = findViewById(R.id.notfication_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
            listView.setAdapter(adapter);
        }
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

        ReminderNotification reminderNotification = new ReminderNotification(this, contest, beforeContest);
        reminderBtn.setOnClickListener(reminderNotification);
    }

}
