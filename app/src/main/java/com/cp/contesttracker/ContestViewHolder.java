package com.cp.contesttracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// ViewHolder class for recycler view

public class ContestViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView time;
    TextView onlineJudge;
    View view;

    public ContestViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.contestName);
        time = itemView.findViewById(R.id.contestTime);
        onlineJudge = itemView.findViewById(R.id.onlineJudge);
        view = itemView;
    }
}
