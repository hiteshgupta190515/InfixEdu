package com.infix.studentmanagement.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.OnlineExam;

import java.util.ArrayList;

public class OnlineExamAdapter extends RecyclerView.Adapter<OnlineExamAdapter.OnlineExamViewHolder>{

    private ArrayList<OnlineExam> onlineExams;

    public OnlineExamAdapter(ArrayList<OnlineExam> onlineExams) {
        this.onlineExams = onlineExams;
    }

    @NonNull
    @Override
    public OnlineExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_exam_row_layout,parent,false);

        return new OnlineExamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineExamViewHolder holder, int position) {

        holder.txtTitle.setText(onlineExams.get(position).getExam_title());
        holder.txtSubject.setText(onlineExams.get(position).getSubject());
        holder.txtdate.setText(onlineExams.get(position).getDate());

        if(onlineExams.get(position).getStatus() == 0){
            holder.txtStatus.setText("Not Yet");
            holder.txtStatus.setBackgroundColor(Color.RED);
        }else{
            holder.txtStatus.setText("alreday submitted");
        }

    }

    @Override
    public int getItemCount() {
        return onlineExams.size();
    }

    public static class OnlineExamViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtSubject;
        TextView txtdate;
        TextView txtStatus;

        public OnlineExamViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtActiveOnlineExamTitle);
            txtSubject = v.findViewById(R.id.onlineExamSubjectName);
            txtdate = v.findViewById(R.id.onlineExamDate);
            txtStatus = v.findViewById(R.id.onlineExamStatus);

        }
    }

}
