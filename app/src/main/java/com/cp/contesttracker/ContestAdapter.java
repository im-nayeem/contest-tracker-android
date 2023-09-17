package com.cp.contesttracker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContestAdapter extends RecyclerView.Adapter<ContestViewHolder> {
    List<Contest> contestList = null;
    Context context;
    View contestView;

    public ContestAdapter(List<Contest> contestList, Context context) {
        this.contestList = contestList;
        this.context = context;
    }


    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);

        ContestViewHolder contestViewHolder = new ContestViewHolder(view);

        return contestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContestViewHolder viewHolder, final int position) {

        final int index = viewHolder.getAdapterPosition();
        viewHolder.name.setText(contestList.get(position).getName());
        viewHolder.time.setText(contestList.get(position).getTimeString());
        viewHolder.host.setText(contestList.get(position).getHost());

//        Log.e("Date: ", contestList.get(position).getTime().toString());
        if(contestList.get(position).isToday())
        {
            viewHolder.name.setBackgroundColor(Color.parseColor("#a0c9a7"));
//            viewHolder.contestView.setBackgroundColor(Color.parseColor("#a0c9a7"));
        }
    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }



    public void updateData(List<Contest> lst)
    {
        this.contestList = lst;
    }
}
