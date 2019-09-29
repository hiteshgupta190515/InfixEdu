package com.infix.edu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    public void onBindViewHolder(@NonNull LeaveViewHolder holder, final int position) {

        holder.txtFrom.setText(leaveLists.get(position).getFrom());
        holder.txtTo.setText(leaveLists.get(position).getTo());
        holder.txtApply.setText(leaveLists.get(position).getApply());
        if(leaveLists.get(position).getStatus().equalsIgnoreCase("P")) {
            holder.txtStatus.setText(R.string.pending);
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        }else if(leaveLists.get(position).getStatus().equalsIgnoreCase("A")) {
            holder.txtStatus.setText(R.string.approved);
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.txtStatus.setText(R.string.rejected);
            holder.txtStatus.setBackgroundColor(Color.RED);
        }

        if(leaveLists.get(position).getRole_id() == 1){

            holder.txtView.setVisibility(View.VISIBLE);

            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context,leaveLists.get(position).getId()+"",Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context,R.style.DialogTheme);
                    View mView = LayoutInflater.from(context).inflate(R.layout.leave_details_layout, null);




                    alertBuilder.setView(mView);
                    AlertDialog dialog = alertBuilder.create();

                    Rect displayRectangle = new Rect();
                    Window window = dialog.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                    mView.setMinimumWidth((int)(displayRectangle.width()));
                    mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.show();

                }
            });

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
        TextView txtView;

        public LeaveViewHolder(@NonNull View v) {
            super(v);

         txtFrom = v.findViewById(R.id.from_date);
         txtTo = v.findViewById(R.id.to_date);
         txtApply = v.findViewById(R.id.apply_date);
         txtStatus = v.findViewById(R.id.status);
         txtView = v.findViewById(R.id.txtLeaveView);

        }
    }

}
