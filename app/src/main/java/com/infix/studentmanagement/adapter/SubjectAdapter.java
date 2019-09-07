package com.infix.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{

    private ArrayList<Subject> subjects;
    private Context ctx;

    public SubjectAdapter(ArrayList<Subject> subjects, Context ctx) {
        this.subjects = subjects;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row_layout,parent,false);

        return new SubjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        holder.txtSubject.setText(subjects.get(position).getSubject());
        holder.txtTeacher.setText(subjects.get(position).getTeacher());

        if(subjects.get(position).getType().equalsIgnoreCase("T")){
            holder.txtType.setText("Theory");
        }else  if(subjects.get(position).getType().equalsIgnoreCase("P")){
            holder.txtType.setText("Practical");
        }else{
            holder.txtType.setText(subjects.get(position).getType());
        }

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder{

        TextView txtSubject;
        TextView txtTeacher;
        TextView txtType;

        public SubjectViewHolder(@NonNull View v) {
            super(v);


            txtSubject = v.findViewById(R.id.subject);
            txtTeacher = v.findViewById(R.id.teacher);
            txtType = v.findViewById(R.id.type);


        }
    }

}
