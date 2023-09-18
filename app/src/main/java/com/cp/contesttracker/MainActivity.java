package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.cp.contesttracker.contest.Contest;
import com.cp.contesttracker.contest.ContestAdapter;
import com.cp.contesttracker.contest.FetchCallBack;
import com.cp.contesttracker.contest.FetchContest;
import com.cp.contesttracker.problemsuggestion.PreferencesActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main Activity class(Controller)

public class MainActivity extends AppCompatActivity implements FetchCallBack {

    private RecyclerView recyclerView;
    private ContestAdapter contestAdapter;
    private ProgressBar progressBar;

    private HashMap<String, List<Contest>> allContestList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find and set the recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // set layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        // Check for updates
        UpdateChecker updateChecker = new UpdateChecker(this, this.getString(R.string.update_api_url));
        updateChecker.checkForUpdate();

        // Create intance of FetchContest class that uses Volley to fetch contest info
        FetchContest fetchContest = new FetchContest();
        fetchContest.fetchAPI(this, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // handle developer option in option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.developer) {
            Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.preferences) {
            Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // callback method called after fetched contests
    @Override
    public void onContestFetch(HashMap<String, List<Contest>> allContestList) {

        this.allContestList = allContestList;
        // sort contests in ascending order according to time
        for(Map.Entry<String,List<Contest>> entry : this.allContestList.entrySet())
        {
            Collections.sort(entry.getValue(), new Comparator<Contest>() {
                @Override
                public int compare(Contest o1, Contest o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
        }
        // show all contests
        contestAdapter = new ContestAdapter(allContestList.get("All"), getApplication());
        recyclerView.setAdapter(contestAdapter);

        // set spinner for filtering contest by host
        setSpinner();

        progressBar.setVisibility(View.GONE);


    }


    // spinner for filtering contest
    private void setSpinner(){

        String temp[] = Contest.getHostList();

        final String[] items = new String[temp.length+2];
        items[0] = "Filter By Contest Host";
        items[1] = "All";

        for(int i=0; i<temp.length; i++)
            items[i+2] = temp[i];

        final Spinner spinner = findViewById(R.id.filter_spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                if (allContestList.containsKey(selectedItem)) {
                    contestAdapter.updateData(allContestList.get(selectedItem));
                    contestAdapter.notifyDataSetChanged();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });

    }

}
