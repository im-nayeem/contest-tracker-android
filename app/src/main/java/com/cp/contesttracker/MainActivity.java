package com.cp.contesttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

// Main Activity class(Controller)

public class MainActivity extends AppCompatActivity implements FetchCallBack {

    private RecyclerView recyclerView;
    private ContestAdapter adapter;

    private List<Contest> contestList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        FetchContest fetchContest = new FetchContest();
        fetchContest.fetchAPI(this, this);

        setSpinner();


    }


    @Override
    public void onContestFetch(List<Contest> contestList) {

        this.contestList = contestList;
        adapter = new ContestAdapter(contestList, getApplication());
        recyclerView.setAdapter(adapter);

    }

    private void setSpinner(){

        String temp[] = Contest.getHostList();

        final String[] items = new String[temp.length+1];
        items[0] = "Filter By Contest Host";

        for(int i=0; i<temp.length; i++)
            items[i+1] = temp[i];



        final Spinner spinner = findViewById(R.id.filter_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Do something with the selected item
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected
            }
        });

    }

}
