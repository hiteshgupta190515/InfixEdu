package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.model.Fee;

import java.util.ArrayList;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.FeeViewHolder>{

    private ArrayList<Fee> fees;
    private Context ctx;

    public FeeAdapter(ArrayList<Fee> fees, Context ctx) {
        this.fees = fees;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public FeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fees_row_layout,parent,false);

        return new FeeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeViewHolder holder, int position) {

        holder.txtTitle.setText(fees.get(position).getTitle());
        holder.txtDueDate.setText(fees.get(position).getDueDate());
        holder.txtFeeAmount.setText("$"+fees.get(position).getAmount());
        holder.txtFeepaid.setText("$"+fees.get(position).getPaid());
        holder.txtFeeBalance.setText("$"+fees.get(position).getBalance());
        holder.txtStatus.setText(fees.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return fees.size();
    }

    public static class FeeViewHolder extends RecyclerView.ViewHolder{


        TextView txtTitle;
        TextView txtDueDate;
        TextView txtFeeAmount;
        TextView txtFeepaid;
        TextView txtFeeBalance;
        TextView txtStatus;

        public FeeViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtFeeTitle);
            txtDueDate = v.findViewById(R.id.dueDate);
            txtFeeAmount = v.findViewById(R.id.feeAmount);
            txtFeepaid = v.findViewById(R.id.feePaid);
            txtFeeBalance = v.findViewById(R.id.feeBalance);
            txtStatus = v.findViewById(R.id.status);

        }
    }

}
