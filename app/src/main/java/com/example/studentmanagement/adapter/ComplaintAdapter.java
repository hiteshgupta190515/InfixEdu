package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.model.Complaint;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.CViewHolder>{

    private ArrayList<Complaint> complaints;
    private Context ctx;

    public ComplaintAdapter(ArrayList<Complaint> complaints, Context ctx) {
        this.complaints = complaints;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_row_layout,parent,false);

        return new CViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder holder, final int position) {

        holder.txtByName.setText(complaints.get(position).getByName());
        holder.txtDate.setText(complaints.get(position).getDate());
        holder.txtAction.setText(complaints.get(position).getAction());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx,complaints.get(position).getByName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public static class CViewHolder extends RecyclerView.ViewHolder{

        TextView txtByName;
        TextView txtDate;
        TextView txtAction;
        LinearLayout linearLayout;

        public CViewHolder(@NonNull View v) {
            super(v);

            txtByName = v.findViewById(R.id.txtByName);
            txtDate = v.findViewById(R.id.txtCompDate);
            txtAction = v.findViewById(R.id.txtAction);
            linearLayout = v.findViewById(R.id.details_complaint);

        }
    }

}
