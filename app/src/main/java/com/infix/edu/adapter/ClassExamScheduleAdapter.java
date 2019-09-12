package com.infix.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.model.ExamSchedule;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;

import java.util.ArrayList;

public class ClassExamScheduleAdapter extends RecyclerView.Adapter<ClassExamScheduleAdapter.ClassScheduleViewHolder>{

    private ArrayList<ExamSchedule> schedules;
    private Context ctx;

    public ClassExamScheduleAdapter(ArrayList<ExamSchedule> schedules, Context ctx) {
        this.schedules = schedules;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ClassScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_exam_schedule_row_layout,parent,false);

        return new ClassScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassScheduleViewHolder holder, int position) {
        holder.txtSubjectName.setText(schedules.get(position).getSubjectName());
        holder.txtRoomNo.setText(schedules.get(position).getRoomNo());
        holder.txtdate.setText(schedules.get(position).getDate());
        holder.txtStart.setText(MyConfig.getAmPm(schedules.get(position).getStart()));
        holder.txtEnd.setText(MyConfig.getAmPm(schedules.get(position).getEnd()));

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public static class ClassScheduleViewHolder extends RecyclerView.ViewHolder{

        TextView txtSubjectName;
        TextView txtdate;
        TextView txtRoomNo;
        TextView txtStart;
        TextView txtEnd;

        public ClassScheduleViewHolder(@NonNull View v) {
            super(v);
            txtSubjectName = v.findViewById(R.id.txtSubjectName);
            txtdate = v.findViewById(R.id.txtClsExamDate);
            txtRoomNo = v.findViewById(R.id.txtClsRoomNo);
            txtStart = v.findViewById(R.id.txtClsExamStart);
            txtEnd = v.findViewById(R.id.txtClsExamEnd);

        }
    }


}
