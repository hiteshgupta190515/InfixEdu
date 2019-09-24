package com.infix.edu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.LeaveList;

import java.util.ArrayList;

public class LeaveListAdapter extends RecyclerView.Adapter<LeaveListAdapter.LeaveViewHolder>{

    private ArrayList<LeaveList> leaveLists;
    private Context context;

    public LeaveListAdapter(ArrayList<LeaveList> leaveLists, Context context) {
        this.leaveLists = leaveLists;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_list_row_layout,parent,false);

        return new LeaveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveViewHolder holder, int position) {

        holder.txtFrom.setText(leaveLists.get(position).getFrom());
        holder.txtTo.setText(leaveLists.get(position).getTo());
        holder.txtApply.setText(leaveLists.get(position).getApply());
        if(leaveLists.get(position).getStatus().equalsIgnoreCase("P")) {
            holder.txtStatus.setText("Pending");
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        }else if(leaveLists.get(position).getStatus().equalsIgnoreCase("A")) {
            holder.txtStatus.setText("Approved");
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.txtStatus.setText("rejected");
            holder.txtStatus.setBackgroundColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return leaveLists.size();
    }

    public static class LeaveViewHolder extends RecyclerView.ViewHolder{

        TextView txtFrom;
        TextView txtTo;
        TextView txtApply;
        TextView txtStatus;

        public LeaveViewHolder(@NonNull View v) {
            super(v);

         txtFrom = v.findViewById(R.id.from_date);
         txtTo = v.findViewById(R.id.to_date);
         txtApply = v.findViewById(R.id.apply_date);
         txtStatus = v.findViewById(R.id.status);


        }
    }

}
