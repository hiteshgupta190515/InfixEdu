package com.infix.edu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.activity.AddHomeWorkActivity;
import com.infix.edu.activity.ApplyLeaveActivity;
import com.infix.edu.activity.AttendenceCalenderActivity;
import com.infix.edu.activity.ContentAddActivity;
import com.infix.edu.activity.HomeActivity;
import com.infix.edu.activity.HomeWorkActivity;
import com.infix.edu.activity.HomeworkTeacherActivity;
import com.infix.edu.activity.LeaveActivity;
import com.infix.edu.activity.LeaveListActivity;
import com.infix.edu.activity.LibraryActivity;
import com.infix.edu.activity.NoticeActivity;
import com.infix.edu.activity.ParentsAboutActivity;
import com.infix.edu.activity.StudentSearchActivity;
import com.infix.edu.activity.TeacherAcademicsActivity;
import com.infix.edu.activity.TeacherAttandenceActivity;
import com.infix.edu.activity.TeacherContentActivity;
import com.infix.edu.activity.TeacherOwnRoutineActivity;
import com.infix.edu.activity.TeacherRoutineSearchActivity;
import com.infix.edu.activity.TeacherStudentActivity;
import com.infix.edu.activity.TeacherSubjectActivity;

import java.util.ArrayList;

public class TeacherHomeAdapter extends RecyclerView.Adapter<TeacherHomeAdapter.ViewHolder>{


    private Activity activity;
    private String[] name;
    private ArrayList<Integer> image;
    private int row_index = -1;
    private Context ctx;

    public TeacherHomeAdapter(Activity activity, String[] name, ArrayList<Integer> image, Context ctx) {
        this.activity = activity;
        this.name = name;
        this.image = image;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_layout_home, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.imageView.setImageResource(image.get(position));
        holder.textView_Name.setText(name[position]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index = position;
                notifyDataSetChanged();

                switch (name[position]) {

                    case "Students":
                        Intent library = new Intent(activity, StudentSearchActivity.class);
                        library.putExtra("status","dashborad");
                        activity.startActivity(library);
                        break;
                    case "Academics":
                        Intent academics = new Intent(activity, TeacherAcademicsActivity.class);
                        activity.startActivity(academics);
                        break;
                    case "Class Routine":
                        Intent classRoutine = new Intent(activity, TeacherRoutineSearchActivity.class);
                        activity.startActivity(classRoutine);
                        break;
                    case "My Routine":
                        Intent myRoutine = new Intent(activity, TeacherOwnRoutineActivity.class);
                        activity.startActivity(myRoutine);
                        break;
                    case "Subjects":
                        Intent subjects = new Intent(activity, TeacherSubjectActivity.class);
                        activity.startActivity(subjects);
                        break;
                    case "Homework":
                        Intent homework = new Intent(activity, HomeworkTeacherActivity.class);
                        activity.startActivity(homework);
                        break;
                    case "Add HW":
                        Intent addhw = new Intent(activity, AddHomeWorkActivity.class);
                        activity.startActivity(addhw);
                        break;
                    case "Attendance":
                        Intent atten = new Intent(activity, TeacherAttandenceActivity.class);
                        activity.startActivity(atten);
                        break;
                    case "My Attendance":
                        Intent myAttend = new Intent(activity, AttendenceCalenderActivity.class);
                        myAttend.putExtra("status","myattendance");
                        activity.startActivity(myAttend);
                        break;
                    case "Search Attendance":
                        Intent srAttend = new Intent(activity, StudentSearchActivity.class);
                        srAttend.putExtra("status","attendance");
                        activity.startActivity(srAttend);
                        break;
                    case "Notice":
                        Intent notice = new Intent(activity, NoticeActivity.class);
                        activity.startActivity(notice);
                        break;
                    case "Library":
                        Intent lib = new Intent(activity, LibraryActivity.class);
                        activity.startActivity(lib);
                        break;
                    case "HW List":
                        Intent hw = new Intent(activity, HomeWorkActivity.class);
                        activity.startActivity(hw);
                        break;
                    case "Leave":
                        Intent leave = new Intent(activity, LeaveActivity.class);
                        activity.startActivity(leave);
                        break;
                    case "Apply Leave":
                        Intent apply = new Intent(activity, ApplyLeaveActivity.class);
                        activity.startActivity(apply);
                        break;
                    case "Leave List":
                        Intent lList = new Intent(activity, LeaveListActivity.class);
                        activity.startActivity(lList);
                        break;
                    case "Contents":
                        activity.startActivity(new Intent(activity, TeacherContentActivity.class));
                        break;
                    case "Upload":
                        activity.startActivity(new Intent(activity, ContentAddActivity.class));
                        break;
                    case "About":
                        activity.startActivity(new Intent(activity, ParentsAboutActivity.class));
                        break;

                }

            }
        });

        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.mipmap.card_select_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.white));
            holder.imageView.setColorFilter(activity.getResources().getColor(R.color.white));
        } else {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.mipmap.bg));
            holder.textView_Name.setTextColor(Color.GRAY);
            holder.imageView.setColorFilter(0);
        }


    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView_Name;
        private LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.home_imageview);
            textView_Name = itemView.findViewById(R.id.textView_home_adapter);
            linearLayout = itemView.findViewById(R.id.linearLayout_home_adapter);

        }
    }

}
