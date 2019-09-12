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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.Fee;

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
    public void onBindViewHolder(@NonNull FeeViewHolder holder, final int position) {

        holder.txtTitle.setText(fees.get(position).getTitle());
        holder.txtDueDate.setText(fees.get(position).getDueDate());
        holder.txtFeeAmount.setText(fees.get(position).getSymbol()+fees.get(position).getAmount());
        holder.txtFeepaid.setText(fees.get(position).getSymbol()+fees.get(position).getPaid());
        holder.txtFeeBalance.setText(fees.get(position).getSymbol()+fees.get(position).getBalance());

        if(fees.get(position).getBalance() > 0 && fees.get(position).getPaid() > 0){

            holder.txtStatus.setText("Partial");
            holder.txtStatus.setBackgroundColor(ctx.getResources().getColor(R.color.yellow));
        }else if(fees.get(position).getBalance() == 0){

            holder.txtStatus.setText("paid");
            holder.txtStatus.setBackgroundColor(Color.GREEN);

        }else{
            holder.txtStatus.setText("unpaid");
            holder.txtStatus.setBackgroundColor(Color.RED);
        }


        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx,R.style.DialogTheme);
                View mView = LayoutInflater.from(ctx).inflate(R.layout.fee_details, null);

                TextView txtTitle = mView.findViewById(R.id.txtFeeTitle);
                TextView txtAmount = mView.findViewById(R.id.amount);
                TextView txtDiscount = mView.findViewById(R.id.feeDiscount);
                TextView txtFine = mView.findViewById(R.id.feeFine);
                TextView txtPaid = mView.findViewById(R.id.paid);
                TextView txtBalance = mView.findViewById(R.id.feeBalance);


                txtTitle.setText(fees.get(position).getTitle());
                txtPaid.setText("$"+fees.get(position).getPaid());
                txtFine.setText("$"+fees.get(position).getFine());
                txtDiscount.setText("$"+fees.get(position).getDiscount());
                txtBalance.setText("$"+fees.get(position).getBalance());
                txtAmount.setText("$"+fees.get(position).getAmount());


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
        TextView txtView;

        public FeeViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtFeeTitle);
            txtDueDate = v.findViewById(R.id.dueDate);
            txtFeeAmount = v.findViewById(R.id.feeAmount);
            txtFeepaid = v.findViewById(R.id.feePaid);
            txtFeeBalance = v.findViewById(R.id.feeBalance);
            txtStatus = v.findViewById(R.id.status);
            txtView = v.findViewById(R.id.txtFeeClassView);
        }
    }

}
