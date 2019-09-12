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
import com.infix.edu.model.Attendence;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>{

    private ArrayList<Attendence> attendences;
    private Context ctx;

    public AttendanceAdapter(ArrayList<Attendence> attendences, Context ctx) {
        this.attendences = attendences;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_row_layout,parent,false);

        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {


        if(attendences.get(position).getP_index() > 0){
            holder.txtDate.setText(attendences.get(position).getDate());
            holder.txtDate.setTextColor(Color.rgb(211,211,211));
        }else{

            holder.viewColor.setVisibility(View.VISIBLE);

            if(attendences.get(position).getStatus().equalsIgnoreCase("A")){

                holder.viewColor.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.circle_background_red));

            }else if(attendences.get(position).getStatus().equalsIgnoreCase("P")){

                holder.viewColor.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.circle_background_green));

            }else if(attendences.get(position).getStatus().equalsIgnoreCase("L")){

                holder.viewColor.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.circle_background_yellow));

            }else if(attendences.get(position).getStatus().equalsIgnoreCase("H")){

                holder.viewColor.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.cirle_background_blue));

            }else if(attendences.get(position).getStatus().equalsIgnoreCase("F")){

                holder.viewColor.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.cirle_background_blue));

            }
            holder.txtDate.setText(attendences.get(position).getDate());
        }


    }

    @Override
    public int getItemCount() {
        return attendences.size();
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder{

        TextView txtDate;
        View viewColor;

        public AttendanceViewHolder(@NonNull View v) {
            super(v);

            txtDate = v.findViewById(R.id.txtAttendanceDate);
            viewColor = v.findViewById(R.id.vAttendanceColor);

        }
    }

}
