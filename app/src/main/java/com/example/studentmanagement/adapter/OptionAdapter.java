package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter <OptionAdapter.OptionViewHolder>{

    String[] items;
    Context ctx;

    public OptionAdapter(String[] items, Context ctx) {
        this.items = items;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_option_item,parent,false);

        return new OptionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {

        holder.txtOptionName.setText(items[position]);

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder{

        TextView txtOptionName;

        public OptionViewHolder(@NonNull View v) {
            super(v);

            txtOptionName = v.findViewById(R.id.txtOptName);

        }
    }

}
