package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.model.Dormitory;

import java.util.ArrayList;

public class DormitoryAdapter extends RecyclerView.Adapter<DormitoryAdapter.DormitoryViewHolder>{

    private ArrayList<Dormitory> dormitories;
    private Context ctx;

    public DormitoryAdapter(ArrayList<Dormitory> dormitories, Context ctx) {
        this.dormitories = dormitories;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public DormitoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dormitory_row_layout,parent,false);

        return new DormitoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DormitoryViewHolder holder, int position) {

        holder.txtTitle.setText(dormitories.get(position).getTitle());
        holder.txtRoomNo.setText(dormitories.get(position).getRoom_no());
        holder.txtBedNo.setText(dormitories.get(position).getNo_of_bed()+"");
        holder.txtBedCost.setText(dormitories.get(position).getCost());

        if(dormitories.get(position).getStatus() == 1)
        holder.txtStatus.setText("available");
        else
            holder.txtStatus.setText("not available");


    }

    @Override
    public int getItemCount() {
        return dormitories.size();
    }

    public static class DormitoryViewHolder extends RecyclerView.ViewHolder{


        TextView txtTitle;
        TextView txtRoomNo;
        TextView txtBedNo;
        TextView txtBedCost;
        TextView txtStatus;

        public DormitoryViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtDormitoryTitle);
            txtRoomNo = v.findViewById(R.id.roomNo);
            txtBedNo = v.findViewById(R.id.bedNoo);
            txtBedCost = v.findViewById(R.id.bedCost);
            txtStatus = v.findViewById(R.id.bedtatus);

        }
    }

}
