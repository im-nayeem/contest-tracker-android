package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// Main Activity class(Controller)

public class MainActivity extends AppCompatActivity implements FetchCallBack {

    private RecyclerView recyclerView;
    private ContestAdapter contestAdapter;

    private HashMap<String, List<Contest>> allContestList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FetchContest fetchContest = new FetchContest();
        fetchContest.fetchAPI(this, this);



    }


    @Override
    public void onContestFetch(HashMap<String, List<Contest>> allContestList) {

        this.allContestList = allContestList;

        contestAdapter = new ContestAdapter(allContestList.get("All"), getApplication());
        recyclerView.setAdapter(contestAdapter);

        setSpinner();


    }

    private void setSpinner(){

        String temp[] = Contest.getHostList();

        final String[] items = new String[temp.length+2];
        items[0] = "Filter By Contest Host";
        items[1] = "All";

        for(int i=0; i<temp.length; i++)
            items[i+1] = temp[i];



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
