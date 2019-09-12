package com.infix.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.Result;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder>{

    private ArrayList<Result> results;
    private Context ctx;

    public ResultAdapter(ArrayList<Result> results, Context ctx) {
        this.results = results;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_row_layout,parent,false);

        return new ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        holder.txtSubjectname.setText(results.get(position).getSubject_name());
        holder.txtFullMarks.setText(results.get(position).getTotal_marks()+"");
        holder.txtObtainedMarks.setText(results.get(position).getMarks_obtain()+"");
        holder.txtGrade.setText(results.get(position).getGrade());
        holder.txtPassMarks.setText(results.get(position).getMarks_pass()+"");

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView txtFullMarks;
        TextView txtObtainedMarks;
        TextView txtSubjectname;
        TextView txtPassMarks;
        TextView txtGrade;

        public ResultViewHolder(@NonNull View v) {
            super(v);

            txtFullMarks = v.findViewById(R.id.resultFullMarks);
            txtObtainedMarks = v.findViewById(R.id.resultMarkObtained);
            txtPassMarks = v.findViewById(R.id.result_pass_marks);
            txtGrade = v.findViewById(R.id.resultGrade);
            txtSubjectname = v.findViewById(R.id.txtResultTitle);

        }
    }

}
