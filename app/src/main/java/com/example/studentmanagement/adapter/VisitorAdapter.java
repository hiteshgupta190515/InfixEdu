package com.example.studentmanagement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.studentmanagement.R;
import com.example.studentmanagement.model.Visitor;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitorAdapter  extends RecyclerView.Adapter<VisitorAdapter.VisitorViewHolder> {

    private ArrayList<Visitor> visitors;
    private Context ctx;

    public VisitorAdapter(ArrayList<Visitor> visitors, Context ctx){
        this.visitors = visitors;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitor_row_layout,parent,false);

        return new VisitorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, final int position) {

        String url =visitors.get(position).getUrl();
        String name = visitors.get(position).getName();
        String purpose = visitors.get(position).getPurpose();
        String noOfVisitor = visitors.get(position).getNoOfVVisitor();
        String phone = visitors.get(position).getPhone();
        String inTime = visitors.get(position).getIn_time();
        String outTime = visitors.get(position).getOut_time();
        String date = visitors.get(position).getDate();

        try {
            Glide.with(ctx)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter()
                    .into(holder.image);
        } catch (Exception e){
        }
        if(name != null && purpose != null){

            holder.txtName.setText(name);
            holder.txtPhone.setText(phone);
            holder.txtNoOfVisitor.setText("Number of visitor: "+noOfVisitor);
            holder.txtpurpose.setText(purpose);
            holder.txtInTime.setText("In time: "+inTime);
            holder.txtOutTime.setText("Out time :"+outTime);
            holder.txtDate.setText("Date: "+date);
        }

    }

    @Override
    public int getItemCount() {
        return visitors.size();
    }

    public class VisitorViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView txtName;
        TextView txtpurpose;
        TextView txtPhone;
        TextView txtNoOfVisitor;
        TextView txtInTime;
        TextView txtOutTime;
        TextView txtDate;
        Toolbar toolbar;


        public VisitorViewHolder(View v) {
            super(v);

            image = v.findViewWithTag(R.id.visitor_poster);
            txtName = v.findViewById(R.id.visitor_name);
            txtpurpose = v.findViewById(R.id.purpose);
            txtPhone = v.findViewById(R.id.phone);
            txtNoOfVisitor = v.findViewById(R.id.txtNoOfVisitor);
            txtInTime = v.findViewById(R.id.txtInTime);
            txtOutTime = v.findViewById(R.id.txtOutTime);
            txtDate = v.findViewById(R.id.txtDate);
            toolbar = v.findViewById(R.id.toolbar);
        }
    }

}
