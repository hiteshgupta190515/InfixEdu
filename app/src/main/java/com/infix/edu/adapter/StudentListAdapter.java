package com.infix.edu.adapter;

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
import com.infix.edu.activity.ProfileActivity;
import com.infix.edu.activity.StudentInformation;
import com.infix.edu.R;
import com.infix.edu.model.Student;
import com.infix.edu.model.Tstudent;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentListAdapter  extends RecyclerView.Adapter<StudentListAdapter.studentViewHolder> {

    private ArrayList<Tstudent> students;
    private Context ctx;

    public StudentListAdapter(ArrayList<Tstudent> students, Context ctx){
        this.students = students;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_row_layout,parent,false);

        return new studentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHolder holder, final int position) {

        final String url =MyConfig.ROOT_URL+students.get(position).getImage();
        String name = students.get(position).getName();
        String des = "Class "+students.get(position).getClassName()+" | Section "+students.get(position).getSectionName()+" | Roll "+students.get(position).getRoll();

        try {
            Glide.with(ctx)
                    .load(url)
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

                Intent intent = new Intent(ctx, ProfileActivity.class);
                intent.putExtra("id",students.get(position).getId());

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
        return students.size();
    }

    public class studentViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView txtName;
        TextView txtDes;
        RelativeLayout layout;

        public studentViewHolder(View v) {
            super(v);

            image = v.findViewById(R.id.student_poster);
            txtName = v.findViewById(R.id.student_name);
            txtDes = v.findViewById(R.id.student_description);
            layout = v.findViewById(R.id.relative_layout);

        }
    }

}
