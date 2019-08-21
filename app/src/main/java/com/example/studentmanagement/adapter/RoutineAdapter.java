package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.model.Routine;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter <RoutineAdapter.RoutineViewHolder>{

    private ArrayList<Routine> routines;
    private Context ctx;

    public RoutineAdapter(ArrayList<Routine> routines, Context ctx) {
        this.routines = routines;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_routine_row_layout,parent,false);

        return new RoutineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {

        holder.txtTime.setText(routines.get(position).getStartTime()+"-"+routines.get(position).getEndTime());
        holder.txtSubject.setText(routines.get(position).getSubject());
        holder.txtRoomNo.setText(routines.get(position).getRoomNo());
        holder.txtWeekName.setText(routines.get(position).getWeekName());

    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder{

        TextView txtTime;
        TextView txtSubject;
        TextView txtRoomNo;
        TextView txtWeekName;

        public RoutineViewHolder(@NonNull View v) {
            super(v);

            txtTime = v.findViewById(R.id.time);
            txtSubject = v.findViewById(R.id.subject);
            txtRoomNo = v.findViewById(R.id.room);
            txtWeekName = v.findViewById(R.id.weekName);

        }
    }

}
