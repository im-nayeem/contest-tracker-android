package com.cp.contesttracker.problemsuggestion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cp.contesttracker.R;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        getSupportActionBar().setTitle("Settings & Preferences");
    }
}
