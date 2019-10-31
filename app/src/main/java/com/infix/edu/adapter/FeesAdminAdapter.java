package com.infix.edu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;

import java.util.ArrayList;

public class FeesAdminAdapter extends RecyclerView.Adapter<FeesAdminAdapter.AdminViewHolder>{

    private ArrayList<SearchData> fees;
    private Activity activity;
    private Helper helper = new Helper();
    private int last_position = -1;

    public FeesAdminAdapter(ArrayList<SearchData> fees, Activity activity) {
        this.fees = fees;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_fees_row,parent,false);

        return new AdminViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, final int position) {

        final String[] strs = fees.get(position).getKey().split("=");

        holder.txtName.setText(strs[0]);
        holder.txtDescription.setText(strs[1]);

        set_animation(holder.mView,position);

        holder.txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity,R.style.DialogTheme);
                View mView = LayoutInflater.from(activity).inflate(R.layout.update_fees_layout, null);


                final EditText etName = mView.findViewById(R.id.etName);
                final EditText etDes = mView.findViewById(R.id.etDescription);
                Button btn_update = mView.findViewById(R.id.btn_update);

                etName.setText(strs[0]);
                etDes.setText(strs[1]);

                alertBuilder.setView(mView);
                AlertDialog dialog = alertBuilder.create();

                Rect displayRectangle = new Rect();
                Window window = dialog.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                mView.setMinimumWidth((int)(displayRectangle.width()));
                mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();


                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name = etName.getText().toString();
                        String description = etDes.getText().toString();
                        String id = String.valueOf(fees.get(position).getValue());

                        helper.update_fees_data(name,description,id,activity);

                    }
                });

            }
        });

    }

    public void set_animation(View v,int position){

        if (position > last_position){

            //Load the animation from the xml file and set it to the row
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.push_left_anim);
            animation.setDuration(1000);
            v.startAnimation(animation);
            last_position = position;
        }

    }

    @Override
    public int getItemCount() {
        return fees.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtDescription;
        TextView txtUpdate;
        View mView;


        public AdminViewHolder(@NonNull View v) {
            super(v);

            mView = v;

            txtName = v.findViewById(R.id.fees_name);
            txtDescription = v.findViewById(R.id.fees_description);
            txtUpdate = v.findViewById(R.id.fees_update);
        }
    }

}
