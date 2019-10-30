package com.infix.edu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.infix.edu.activity.AttendenceCalenderActivity;
import com.infix.edu.activity.ProfileActivity;
import com.infix.edu.activity.StudentInformation;
import com.infix.edu.R;
import com.infix.edu.activity.TeacherStudentActivity;
import com.infix.edu.model.Student;
import com.infix.edu.model.Tstudent;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentListAdapter  extends RecyclerView.Adapter<StudentListAdapter.studentViewHolder> {

    private ArrayList<Tstudent> students;
    private Context ctx;
    private String status;
    private int last_position = -1;
    public static ArrayList<String> ids = new ArrayList<>();
    public static ArrayList<String> abs = new ArrayList<>();

    public StudentListAdapter(ArrayList<Tstudent> students, Context ctx,String status){
        this.students = students;
        this.ctx = ctx;
        this.status = status;
        ids.clear();
        abs.clear();
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_row_layout,parent,false);

        return new studentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final studentViewHolder holder, final int position) {

        final String url =MyConfig.ROOT_URL+students.get(position).getImage();
        final String name = students.get(position).getName();
        String des = "Class "+students.get(position).getClassName()+" | Section "+students.get(position).getSectionName()+" | Roll "+students.get(position).getRoll();
        final boolean[] isSelected = {false};

        try {
            Glide.with(ctx)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .fitCenter()
                    .into(holder.image);
         }catch (Exception e){}

        if(name != null && des != null){

            holder.txtName.setText(name);
            holder.txtDes.setText(des);

        }

        set_animation(holder.mView,position);

        if(status.equalsIgnoreCase("admin_attendance")){

            holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if(isSelected[0]){
                        try {
                            isSelected[0] = false;

                            Glide.with(ctx)
                                    .load(url)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .fitCenter()
                                    .into(holder.image);

                        } catch (Exception e){
                        }
                    }else{

                        Snackbar.make(holder.mView, students.get(position).getName()+" added to the list", Snackbar.LENGTH_SHORT)
                                .show();

                        isSelected[0] = true;

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if(isSelected[0]){

                                    ids.add(String.valueOf(students.get(position).getId()));
                                    abs.add(TeacherStudentActivity.selected_radio_btn);
                                    students.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        },1000);

                        holder.image.setImageDrawable(ctx.getResources().getDrawable(R.mipmap.ic_password_check));
                    }
                    return false;
                }
            });


        }else{

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent;

                    if(status.equalsIgnoreCase("attendance")){
                        intent = new Intent(ctx, AttendenceCalenderActivity.class);
                        intent.putExtra("status",status);
                    }else{
                        intent = new Intent(ctx, ProfileActivity.class);
                    }

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

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class studentViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView txtName;
        TextView txtDes;
        RelativeLayout layout;
        View mView;

        public studentViewHolder(View v) {
            super(v);

            image = v.findViewById(R.id.student_poster);
            txtName = v.findViewById(R.id.student_name);
            txtDes = v.findViewById(R.id.student_description);
            layout = v.findViewById(R.id.relative_layout);
            mView = v;

        }

    }

}
