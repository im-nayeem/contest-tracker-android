package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ContestDetailsActivity extends AppCompatActivity {
    private TextView contestName;
    private TextView contestTime;
    private TextView contestDuration;
    private TextView contestHost;
    private TextView contestLink;
    private Contest contest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_details);

        Intent intent = getIntent();
        this.contest = (Contest) intent.getSerializableExtra("contest");
        this.contestName = findViewById(R.id.contest_name);
        this.contestTime = findViewById(R.id.contest_time);
        this.contestHost = findViewById(R.id.contest_host);
        this.contestDuration = findViewById(R.id.contest_duration);
        this.contestLink = findViewById(R.id.contest_link);

        setData();

    }
    private void setData()
    {
        this.contestName.setText(this.contest.getName());
        this.contestDuration.setText(this.contest.getDuration());
        this.contestHost.setText(this.contest.getHost());
        this.contestLink.setText("Click to see details: \n" + this.contest.getContestLink());
    }

}
