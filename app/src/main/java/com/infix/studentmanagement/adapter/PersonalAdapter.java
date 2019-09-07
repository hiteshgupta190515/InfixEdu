package com.infix.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.PersonalData;

import java.util.ArrayList;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder>{

    private ArrayList<PersonalData> personalData;
    private Context ctx;

    public PersonalAdapter(ArrayList<PersonalData> personalData, Context ctx) {
        this.personalData = personalData;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_row_layout,parent,false);

        return new PersonalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalViewHolder holder, int position) {

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

        public PersonalViewHolder(@NonNull View v) {
            super(v);

            txtKey = v.findViewById(R.id.p_key);
            txtvalue = v.findViewById(R.id.p_value);

        }
    }

}
