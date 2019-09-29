package com.infix.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.PersonalData;

import java.util.ArrayList;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>{

    private ArrayList<PersonalData> personalData;
    private Context ctx;
    private int last_position = -1;

    public PersonalAdapter(ArrayList<PersonalData> personalData, Context ctx) {
        this.personalData = personalData;
        this.ctx = ctx;
    }

    public void set_animation(View v,int position){

        if (position > last_position){

            //Load the animation from the xml file and set it to the row
            Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.push_left_anim);
            animation.setDuration(500);
            v.startAnimation(animation);
            last_position = position;
        }

    }

    @NonNull
    @Override
    public PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_row_layout,parent,false);

        return new PersonalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalViewHolder holder, int position) {

        set_animation(holder.mView,position);

        holder.txtKey.setText(personalData.get(position).getKey());
        holder.txtvalue.setText(personalData.get(position).getValue());

    }

    @Override
    public int getItemCount() {
        return personalData.size();
    }

    public static class PersonalViewHolder extends RecyclerView.ViewHolder{

        TextView txtKey;
        TextView txtvalue;
        View mView;

        public PersonalViewHolder(@NonNull View v) {
            super(v);

            mView = v;

            txtKey = v.findViewById(R.id.p_key);
            txtvalue = v.findViewById(R.id.p_value);

        }
    }

}
