package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// Main Activity class(Controller)

public class MainActivity extends AppCompatActivity implements FetchCallBack {

    private List<Contest> contestList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FetchContest fetchContest = new FetchContest();
        fetchContest.fetchAPI(this, this);

    }


    @Override
    public void onContestFetch(List<Contest> contestList) {
        Log.e("Err==========","kaj korena");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ContestAdapter adapter = new ContestAdapter(contestList, getApplication());
        recyclerView.setAdapter(adapter);

    }
}
