package com.infix.studentmanagement.adapter;

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

import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.activity.ActiveOnlineExamActivity;
import com.infix.studentmanagement.activity.AdminSectionActivity;
import com.infix.studentmanagement.activity.AttendenceCalenderActivity;
import com.infix.studentmanagement.activity.ChildListActivity;
import com.infix.studentmanagement.activity.ClassExamActivity;
import com.infix.studentmanagement.activity.ClassExamResultActivity;
import com.infix.studentmanagement.activity.ClassExamScheduleActivity;
import com.infix.studentmanagement.activity.ClassRoutineActivity;
import com.infix.studentmanagement.activity.DormitoryActivity;
import com.infix.studentmanagement.activity.ExamChooseActivity;
import com.infix.studentmanagement.activity.FeesActivity;
import com.infix.studentmanagement.activity.HomeWorkActivity;
import com.infix.studentmanagement.activity.InventoryActivity;
import com.infix.studentmanagement.activity.IssuedBookActivity;
import com.infix.studentmanagement.activity.LibraryActivity;
import com.infix.studentmanagement.activity.NoticeActivity;
import com.infix.studentmanagement.activity.OnlineExamActivity;
import com.infix.studentmanagement.activity.ProfileActivity;
import com.infix.studentmanagement.activity.StudentLibraryActivity;
import com.infix.studentmanagement.activity.StudentListActivity;
import com.infix.studentmanagement.activity.StudentSubjectActivity;
import com.infix.studentmanagement.activity.StudentTeacherActivity;
import com.infix.studentmanagement.activity.TimelineActivity;
import com.infix.studentmanagement.activity.TransportActivity;

import java.util.ArrayList;


public class HomeAdapterHome extends RecyclerView.Adapter<HomeAdapterHome.ViewHolder> {

    private Activity activity;
    private String[] name;
    private ArrayList<Integer> image;
    private int row_index = -1;
    private Context ctx;
    private int id;


    public HomeAdapterHome(Activity activity, String[] name, ArrayList<Integer> image,int id,Context ctx) {
        this.activity = activity;
        this.name = name;
        this.image = image;
        this.ctx =ctx;
        this.id = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_layout_home, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Picasso.with(activity).load(image[position]).into(holder.imageView);
        holder.imageView.setImageResource(image.get(position));
        holder.textView_Name.setText(name[position]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

                switch (name[position]){

                    case "Student":
                        activity.startActivity(new Intent(activity, StudentListActivity.class));
                        break;
                    case "Library":
                        Intent library = new Intent(activity, StudentLibraryActivity.class);
                        library.putExtra("id",id);
                        activity.startActivity(library);
                        break;
                    case "Inventory":
                        activity.startActivity(new Intent(activity, InventoryActivity.class));
                        break;
                    case "Admin Section":
                        activity.startActivity(new Intent(activity, AdminSectionActivity.class));
                        break;
                    case "Profile":
                        Intent profile = new Intent(activity, ProfileActivity.class);
                        profile.putExtra("id",id);
                        activity.startActivity(profile);
                        break;
                    case "Fees":
                        Intent fees = new Intent(activity, FeesActivity.class);
                        fees.putExtra("id",id);
                        activity.startActivity(fees);
                        break;
                    case "Routine":
                        Intent routines = new Intent(activity, ClassRoutineActivity.class);
                        routines.putExtra("id",id);
                        activity.startActivity(routines);
                        break;
                    case "Homework":
                        Intent homeworks = new Intent(activity, HomeWorkActivity.class);
                        homeworks.putExtra("id",id);
                        activity.startActivity(homeworks);
                        break;
                    case "Notice":
                        Intent notices = new Intent(activity, NoticeActivity.class);
                        notices.putExtra("id",id);
                        activity.startActivity(notices);
                        break;
                    case "Subjects":
                        Intent subject = new Intent(activity, StudentSubjectActivity.class);
                        subject.putExtra("id",id);
                        activity.startActivity(subject);
                        break;
                    case "Teacher":
                        Intent teacher = new Intent(activity, StudentTeacherActivity.class);
                        teacher.putExtra("id",id);
                        activity.startActivity(teacher);
                        break;
                    case "Books List":
                        activity.startActivity(new Intent(activity, LibraryActivity.class));
                        break;
                    case "Books Issued":
                        Intent issued = new Intent(activity, IssuedBookActivity.class);
                        issued.putExtra("id",id);
                        activity.startActivity(issued);
                        break;
                    case "Dormitory":
                        Intent dormitory = new Intent(activity, DormitoryActivity.class);
                        dormitory.putExtra("id",id);
                        activity.startActivity(dormitory);
                        break;
                    case "Transport":
                        Intent transport = new Intent(activity, TransportActivity.class);
                        transport.putExtra("id",id);
                        activity.startActivity(transport);
                        break;
                    case "Timeline":
                        Intent timelines = new Intent(activity, TimelineActivity.class);
                        timelines.putExtra("id",id);
                        activity.startActivity(timelines);
                        break;
                    case "Attendance":
                        Intent attends = new Intent(activity, AttendenceCalenderActivity.class);
                        attends.putExtra("id",id);
                        activity.startActivity(attends);
                        break;
                    case "Online Exam":
                        Intent onlineExam = new Intent(activity, OnlineExamActivity.class);
                        onlineExam.putExtra("id",id);
                        activity.startActivity(onlineExam);
                        break;
                    case "Active Exams":
                        Intent activeOnlineExam = new Intent(activity, ActiveOnlineExamActivity.class);
                        activeOnlineExam.putExtra("id",id);
                        activity.startActivity(activeOnlineExam);
                        break;
                    case "Exam Result":
                        Intent activeResultOnlineExam = new Intent(activity, ExamChooseActivity.class);
                        activeResultOnlineExam.putExtra("id",id);
                        activity.startActivity(activeResultOnlineExam);
                        break;
                    case "Examination":
                        Intent classExam = new Intent(activity, ClassExamActivity.class);
                        classExam.putExtra("id",id);
                        activity.startActivity(classExam);
                        break;
                    case "Schedule":
                        Intent schedule = new Intent(activity, ClassExamScheduleActivity.class);
                        schedule.putExtra("id",id);
                        activity.startActivity(schedule);
                        break;
                    case "Result":
                        Intent classResult = new Intent(activity, ClassExamResultActivity.class);
                        classResult.putExtra("id",id);
                        activity.startActivity(classResult);
                        break;
                    case "My Child":
                        activity.startActivity(new Intent(activity, ChildListActivity.class));
                        break;
                    default:
                        break;

                }

            }
        });

        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.mipmap.card_select_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.drawar_text_select));
            holder.imageView.setColorFilter(activity.getResources().getColor(R.color.drawar_image_select));
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

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}
