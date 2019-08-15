package com.example.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.model.Inventory;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter <InventoryAdapter.InventoryViewHolder>{

    private ArrayList<Inventory> inventories = new ArrayList<>();
    private Context ctx;

    public InventoryAdapter(ArrayList<Inventory> inventories, Context ctx) {
        this.inventories = inventories;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_row_layout,parent,false);

        return new InventoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {

        holder.txtName.setText("Item name : "+inventories.get(position).getName());
        holder.txtDes.setText("Description : "+inventories.get(position).getDescription());
        holder.txtStock.setText("Total in stock : "+inventories.get(position).getTotal_in_stock());
        holder.txtCategory.setText("Category name : "+inventories.get(position).getCategory_name());

    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtDes;
        TextView txtStock;
        TextView txtCategory;

        public InventoryViewHolder(@NonNull View v) {
            super(v);

            txtName = v.findViewById(R.id.txtInventoryName);
            txtDes = v.findViewById(R.id.txtInventoryDescription);
            txtStock = v.findViewById(R.id.txtInventoryStock);
            txtCategory = v.findViewById(R.id.txtInventoryCategory);

        }
    }

}
