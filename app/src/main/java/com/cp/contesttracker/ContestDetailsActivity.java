package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        final Intent intent = getIntent();
        this.contest = (Contest) intent.getSerializableExtra("contest");
        this.contestName = findViewById(R.id.contest_name);
        this.contestTime = findViewById(R.id.contest_time);
        this.contestHost = findViewById(R.id.contest_host);
        this.contestDuration = findViewById(R.id.contest_duration);
        this.contestLink = findViewById(R.id.contest_link);

        this.contestLink.setOnClickListener(new View.OnClickListener() {
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

}
