package com.infix.studentmanagement.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.activity.StudentInformation;
import com.infix.studentmanagement.model.Child;
import com.infix.studentmanagement.myconfig.MyConfig;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ChildViewHolder> {

    private ArrayList<Child> childs;
    private Context ctx;

    public ChildListAdapter(ArrayList<Child> childs, Context ctx){
        this.childs = childs;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_row_layout,parent,false);

        return new ChildViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, final int position) {

        String url =childs.get(position).getImgUrl();
        String name = childs.get(position).getName();
        String des = "Class "+childs.get(position).getmClass()+" | "+"Section "+childs.get(position).getSection()+" | Roll "+childs.get(position).getRoll();

        try {
            Glide.with(ctx)
                    .load(MyConfig.ROOT_URL+url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter()
                    .into(holder.image);
        } catch (Exception e){
        }
        if(name != null && des != null){

            holder.txtName.setText(name);
            holder.txtDes.setText(des);

        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(ctx,students.get(position).getFull_name(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ctx, StudentInformation.class);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {

                    Pair<View, String> p1 = Pair.create(view.findViewById(R.id.student_poster), "profile");
                    Pair<View, String> p2 = Pair.create(view.findViewById(R.id.student_name), "name");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation((Activity) ctx, p1, p2);
                    ctx.startActivity(intent, options.toBundle());

                } else {
                    ctx.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return childs.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView txtName;
        TextView txtDes;
        RelativeLayout layout;

        public ChildViewHolder(View v) {
            super(v);

            image = v.findViewWithTag(R.id.student_poster);
            txtName = v.findViewById(R.id.student_name);
            txtDes = v.findViewById(R.id.student_description);
            layout = v.findViewById(R.id.relative_layout);

        }
    }

}
