package com.cp.contesttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        FetchContest fetchContest = new FetchContest();
        fetchContest.fetchAPI(this, this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.developer) {
            Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onContestFetch(HashMap<String, List<Contest>> allContestList) {

        this.allContestList = allContestList;

        for(Map.Entry<String,List<Contest>> entry : this.allContestList.entrySet())
        {
            Collections.sort(entry.getValue(), new Comparator<Contest>() {
                @Override
                public int compare(Contest o1, Contest o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
        }

        contestAdapter = new ContestAdapter(allContestList.get("All"), getApplication());
        recyclerView.setAdapter(contestAdapter);


        setSpinner();

        progressBar.setVisibility(View.GONE);


    }



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
