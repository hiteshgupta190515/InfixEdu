package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.model.HomeWork;

import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MViewHolder>{

    private ArrayList<HomeWork> homeWorks;
    private Context ctx;

    public HomeWorkAdapter(ArrayList<HomeWork> homeWorks, Context ctx) {
        this.homeWorks = homeWorks;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row_layout,parent,false);

        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {

        holder.txtTitle.setText(homeWorks.get(position).getTitle());
        holder.txtCreated.setText(homeWorks.get(position).getHomeworkDate());
        holder.txtSubmission.setText(homeWorks.get(position).getSubmissionDate());
        holder.txtEvaluation.setText(homeWorks.get(position).getEvaluationdate());
        holder.txtStatus.setText(homeWorks.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return homeWorks.size();
    }

    public static class MViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDownload;
        TextView txtView;
        TextView txtCreated;
        TextView txtSubmission;
        TextView txtEvaluation;
        TextView txtStatus;

        public MViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtHWTitle);
            txtDownload = v.findViewById(R.id.txtDownload);
            txtView = v.findViewById(R.id.txtHWClassView);
            txtCreated = v.findViewById(R.id.created);
            txtStatus = v.findViewById(R.id.status);
            txtSubmission = v.findViewById(R.id.submission);
            txtEvaluation = v.findViewById(R.id.evaluation);
        }
    }

}
