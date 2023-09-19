package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.cp.contesttracker.contest.Contest;
import com.cp.contesttracker.contest.ContestAdapter;
import com.cp.contesttracker.contest.FetchCallBack;
import com.cp.contesttracker.contest.FetchContest;
import com.cp.contesttracker.problemsuggestion.AppPreferences;
import com.cp.contesttracker.problemsuggestion.PreferencesActivity;
import com.cp.contesttracker.problemsuggestion.WebViewActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main Activity class(Controller)
public class MainActivity extends AppCompatActivity implements FetchCallBack {

    private RecyclerView recyclerView;
    private ContestAdapter contestAdapter;
    private ProgressBar progressBar;
    private Button datePicker;

    private HashMap<String, List<Contest>> allContestList = null;
    private List<Contest> currentContestList = null;



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

        datePicker = findViewById(R.id.date_picker);

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

    // handle options in action bar option menu
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
        } else if (id == R.id.recommender || id == R.id.stat) {
            AppPreferences preferences = new AppPreferences(MainActivity.this);
            if(preferences.getRecommenderUrl().equals(""))
            {
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            } else {
//                Log.e("URL----------", preferences.getRecommenderUrl());
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", preferences.getRecommenderUrl());
                if (id == R.id.stat)
                    intent.putExtra("type", "stat");
                else
                    intent.putExtra("type", "recommendation");
                startActivity(intent);
                return true;
            }
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
        this.currentContestList = allContestList.get("All");

        // set spinner for filtering contest by host
        setSpinner();

        // set date picker listener
        setDatePickerListener();

        progressBar.setVisibility(View.GONE);


    }


    // spinner for filtering contest
    private void setSpinner() {

        String temp[] = Contest.getHostList();

        final String[] items = new String[temp.length+1];
        items[0] = "All";

        for(int i=0; i<temp.length; i++)
            items[i+1] = temp[i];

        final Spinner spinner = findViewById(R.id.filter_spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datePicker.setText(R.string.date_picker);
                String selectedItem = (String) parent.getItemAtPosition(position);

                if (allContestList.containsKey(selectedItem)) {
                    currentContestList = allContestList.get(selectedItem);
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

    // set event listener for datePicker button
    private void setDatePickerListener() {
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get instance of calendar.
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);

                // variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                LocalDate date = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                                datePicker.setText(date.toString());
                                filterContestByDate(date.toString());
                            }

                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    // filter contest according to date and update recyclerview
    private void filterContestByDate(String date) {
        List<Contest>filteredContest = new ArrayList<>();
        for (Contest contest: this.currentContestList) {
            if(Utility.isDateMatched(date, contest.getTime()))
            {
                filteredContest.add(contest);
            }
        }
        contestAdapter.updateData(filteredContest);
        contestAdapter.notifyDataSetChanged();
    }
}
