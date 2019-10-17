package com.infix.edu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.SearchData;

import java.util.ArrayList;

import io.armcha.elasticview.ElasticView;

public class OptionAdapter extends RecyclerView.Adapter <OptionAdapter.OptionViewHolder>{

    private static ArrayList<SearchData> items;
    private Context ctx;
    private static ClickListener clickListener;
    private int row_index = 0;

    public OptionAdapter(ArrayList<SearchData> items, Context ctx) {
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
    public void onBindViewHolder(@NonNull OptionViewHolder holder, final int position) {

        holder.txtOptionName.setText(items.get(position).getKey());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index = position;
                notifyDataSetChanged();

                if (clickListener != null) {
                    clickListener.itemClicked(items.get(position).getKey(), position,view);
                }
            }
        });

        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(ctx.getResources().getDrawable(R.drawable.toolbar_gradient));
            holder.txtOptionName.setTextColor(ctx.getResources().getColor(R.color.white));
        } else {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
            holder.txtOptionName.setTextColor(Color.GRAY);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {

        void itemClicked(String s, int position, View view);

    }


    public static class OptionViewHolder extends RecyclerView.ViewHolder{

        TextView txtOptionName;
        private ElasticView linearLayout;

        public OptionViewHolder(@NonNull View v) {
            super(v);

            txtOptionName = v.findViewById(R.id.txtOptName);
            linearLayout = itemView.findViewById(R.id.linearLayout_home_adapter);

        }
    }

}
