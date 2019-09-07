package com.infix.studentmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.TimeLine;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class TimlineAdapter extends RecyclerView.Adapter<TimlineAdapter.TimelineViewHolder> {

    private ArrayList<TimeLine> timeLines;

    public TimlineAdapter(ArrayList<TimeLine> timeLines) {
        this.timeLines = timeLines;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_row_layout,parent,false);

        return new TimelineViewHolder(v,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {

        holder.txtTitle.setText(timeLines.get(position).getTitle());
        holder.txtDate.setText(timeLines.get(position).getDate());
        holder.txtDescription.setText(timeLines.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return timeLines.size();
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView txtTitle;
        private AppCompatTextView txtDate;
        private AppCompatTextView txtDescription;
        private TimelineView mTimelineView;


        public TimelineViewHolder(@NonNull View v,int viewType) {
            super(v);

            txtTitle = v.findViewById(R.id.text_timeline_title);
            txtDate = v.findViewById(R.id.text_timeline_date);
            txtDescription = v.findViewById(R.id.text_timeline_description);
            mTimelineView = v.findViewById(R.id.timeline);
            mTimelineView.initLine(viewType);
        }
    }

}
