package com.cp.contesttracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// ViewHolder class for recycler view

public class ContestViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView name;
    TextView time;
    TextView host;

    public ContestViewHolder(@NonNull final View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.contestName);
        time = itemView.findViewById(R.id.contestTime);
        host = itemView.findViewById(R.id.host);
        view = itemView;

    }
}
