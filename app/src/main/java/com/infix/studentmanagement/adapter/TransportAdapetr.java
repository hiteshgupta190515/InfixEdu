package com.infix.studentmanagement.adapter;

import android.content.Context;
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

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.Transport;

import java.util.ArrayList;

public class TransportAdapetr extends RecyclerView.Adapter<TransportAdapetr.TransportViewHolder>{

    private ArrayList<Transport> transports;
    private Context ctx;

    public TransportAdapetr(ArrayList<Transport> transports, Context ctx) {
        this.transports = transports;
        this.ctx = ctx;
    }

    @Override
    public TransportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_row_layout,parent,false);

        return new TransportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransportViewHolder holder, final int position) {

        holder.txtRoute.setText(transports.get(position).getRoute());
        holder.txtVahicleNo.setText(transports.get(position).getVehicle_no());

        holder.txtVahicleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx,R.style.DialogTheme);
                View mView = LayoutInflater.from(ctx).inflate(R.layout.transport_details_row_layout, null);

                TextView txtRoute = mView.findViewById(R.id.txtDRoute);
                TextView txtVehicleNo = mView.findViewById(R.id.txtDVehicleNo);
                TextView txtVehicleModel = mView.findViewById(R.id.txtDVehicalModel);
                TextView txtDVehileDriver = mView.findViewById(R.id.txtDVehicalDriverName);
                TextView txtDriverContact = mView.findViewById(R.id.txtDVehicalContact);
                TextView txtMadeDate = mView.findViewById(R.id.txtDVehicleMadeYear);
                TextView txtVehicleLicence = mView.findViewById(R.id.txtDVehicleLicence);


                txtRoute.setText("Route : "+transports.get(position).getRoute());
                txtDVehileDriver.setText(transports.get(position).getDriver_name());
                txtDriverContact.setText(transports.get(position).getDriver_contact());
                txtMadeDate.setText(transports.get(position).getMade_year());
                txtVehicleModel.setText(transports.get(position).getVehicle_model());
                txtVehicleNo.setText(transports.get(position).getVehicle_no());
                txtVehicleLicence.setText(transports.get(position).getDriving_license());




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
        return transports.size();
    }

    public static class TransportViewHolder extends RecyclerView.ViewHolder{

        TextView txtRoute;
        TextView txtVahicleNo;
        TextView txtVahicleView;

        public TransportViewHolder(@NonNull View v) {
            super(v);

            txtRoute = v.findViewById(R.id.txtTransportTitle);
            txtVahicleNo = v.findViewById(R.id.txtVehicleNo);
            txtVahicleView = v.findViewById(R.id.txtTransportView);

        }
    }

}
