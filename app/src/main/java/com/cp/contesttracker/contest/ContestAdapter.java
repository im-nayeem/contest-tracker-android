package com.cp.contesttracker.contest;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cp.contesttracker.R;

import java.io.Serializable;
import java.util.List;

public class ContestAdapter extends RecyclerView.Adapter<ContestViewHolder> {
    List<Contest> contestList = null;
    Context context;

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

        return new ContestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContestViewHolder viewHolder, final int position) {

//        final int index = viewHolder.getAdapterPosition();
        viewHolder.name.setText(contestList.get(position).getName());
        viewHolder.time.setText(contestList.get(position).getTimeString());
        viewHolder.host.setText(contestList.get(position).getHost());


        if (contestList.get(position).isToday()) {
            Drawable backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.active_layout_border);
            viewHolder.view.setBackground(backgroundDrawable);
        } else {
//            viewHolder.view.setBackgroundColor(Color.parseColor("#E9F9FAFF"));
            Drawable backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.layout_border);
            viewHolder.view.setBackground(backgroundDrawable);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContestDetailsActivity.class);
                Contest contest = contestList.get(position);
                intent.putExtra("contest", (Serializable) contest);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }

    public void updateData(List<Contest> lst) {
        this.contestList = lst;
    }

}
