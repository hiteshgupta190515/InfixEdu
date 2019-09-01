package com.example.studentmanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.MainActivity;
import com.example.studentmanagement.R;
import com.example.studentmanagement.activity.ActiveOnlineExamActivity;
import com.example.studentmanagement.activity.AdminSectionActivity;
import com.example.studentmanagement.activity.AttendenceCalenderActivity;
import com.example.studentmanagement.activity.ClassRoutineActivity;
import com.example.studentmanagement.activity.DormitoryActivity;
import com.example.studentmanagement.activity.ExamChooseActivity;
import com.example.studentmanagement.activity.FeesActivity;
import com.example.studentmanagement.activity.HomeWorkActivity;
import com.example.studentmanagement.activity.InventoryActivity;
import com.example.studentmanagement.activity.IssuedBookActivity;
import com.example.studentmanagement.activity.LibraryActivity;
import com.example.studentmanagement.activity.LoginActivity;
import com.example.studentmanagement.activity.NoticeActivity;
import com.example.studentmanagement.activity.OnlineExamActivity;
import com.example.studentmanagement.activity.ProfileActivity;
import com.example.studentmanagement.activity.StudentLibraryActivity;
import com.example.studentmanagement.activity.StudentListActivity;
import com.example.studentmanagement.activity.StudentSubjectActivity;
import com.example.studentmanagement.activity.StudentTeacherActivity;
import com.example.studentmanagement.activity.TimelineActivity;
import com.example.studentmanagement.activity.TransportActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeAdapterHome extends RecyclerView.Adapter<HomeAdapterHome.ViewHolder> {

    private Activity activity;
    private String[] name;
    private ArrayList<Integer> image;
    private int row_index = -1;
    private Context ctx;


    public HomeAdapterHome(Activity activity, String[] name, ArrayList<Integer> image,Context ctx) {
        this.activity = activity;
        this.name = name;
        this.image = image;
        this.ctx =ctx;
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
                        activity.startActivity(new Intent(activity, StudentLibraryActivity.class));
                        break;
                    case "Inventory":
                        activity.startActivity(new Intent(activity, InventoryActivity.class));
                        break;
                    case "Admin Section":
                        activity.startActivity(new Intent(activity, AdminSectionActivity.class));
                        break;
                    case "Profile":
                        activity.startActivity(new Intent(activity, ProfileActivity.class));
                        break;
                    case "Fees":
                        activity.startActivity(new Intent(activity, FeesActivity.class));
                        break;
                    case "Routine":
                        activity.startActivity(new Intent(activity, ClassRoutineActivity.class));
                        break;
                    case "Homework":
                        activity.startActivity(new Intent(activity, HomeWorkActivity.class));
                        break;
                    case "Notice":
                        activity.startActivity(new Intent(activity, NoticeActivity.class));
                        break;
                    case "Subjects":
                        activity.startActivity(new Intent(activity, StudentSubjectActivity.class));
                        break;
                    case "Teacher":
                        activity.startActivity(new Intent(activity, StudentTeacherActivity.class));
                        break;
                    case "Books List":
                        activity.startActivity(new Intent(activity, LibraryActivity.class));
                        break;
                    case "Books Issued":
                        activity.startActivity(new Intent(activity, IssuedBookActivity.class));
                        break;
                    case "Dormitory":
                        activity.startActivity(new Intent(activity, DormitoryActivity.class));
                        break;
                    case "Transport":
                        activity.startActivity(new Intent(activity, TransportActivity.class));
                        break;
                    case "Timeline":
                        activity.startActivity(new Intent(activity, TimelineActivity.class));
                        break;
                    case "Attendance":
                        activity.startActivity(new Intent(activity, AttendenceCalenderActivity.class));
                        break;
                    case "Online Exam":
                        activity.startActivity(new Intent(activity, OnlineExamActivity.class));
                        break;
                    case "Active Exams":
                        activity.startActivity(new Intent(activity, ActiveOnlineExamActivity.class));
                        break;
                    case "Exam Result":
                        activity.startActivity(new Intent(activity, ExamChooseActivity.class));
                        break;
                    case "Logout":

                        SharedPreferences pref = ctx.getSharedPreferences("default", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();

                        editor.putBoolean("isLoged", false);

                        activity.startActivity(new Intent(activity, LoginActivity.class));
                        activity.finish();
                        break;
                    default:
                        break;

                }

            }
        });

        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.draware_selected_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.drawar_text_select));
             holder.imageView.setColorFilter(activity.getResources().getColor(R.color.drawar_image_select));
        } else {
            holder.linearLayout.setBackgroundColor(activity.getResources().getColor(R.color.drawar_layout_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.black));
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
