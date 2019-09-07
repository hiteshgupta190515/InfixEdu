package com.infix.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.Child;

import java.util.ArrayList;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder>{

    private ArrayList<Child> children;
    private Context ctx;

    public ChildAdapter(ArrayList<Child> children, Context ctx) {
        this.children = children;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_row_layout,parent,false);

        return new ChildViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {

        holder.txtName.setText(children.get(position).getName());
        holder.txtDes.setText(children.get(position).getmClass());

    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    public static class ChildViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtDes;

        public ChildViewHolder(@NonNull View v) {
            super(v);

            txtName = v.findViewById(R.id.child_name);
            txtDes = v.findViewById(R.id.child_description);

        }
    }

}
