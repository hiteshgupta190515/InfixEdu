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
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.activity.AddBookActivity;
import com.infix.edu.activity.AddDormitoryActivity;
import com.infix.edu.activity.AddDormitoryRoomActivity;
import com.infix.edu.activity.AddFeesActivity;
import com.infix.edu.activity.AddRouteActivity;
import com.infix.edu.activity.AddVehicleActivity;
import com.infix.edu.activity.AdminDormitoryActivity;
import com.infix.edu.activity.AdminDormitoryDashActivity;
import com.infix.edu.activity.AdminLeaveActivity;
import com.infix.edu.activity.AdminStaffListActivity;
import com.infix.edu.activity.AdminTransportActivity;
import com.infix.edu.activity.FeesAdminActivity;
import com.infix.edu.activity.FeesListActivity;
import com.infix.edu.activity.LibraryActivity;
import com.infix.edu.activity.LibraryAdminActivity;
import com.infix.edu.activity.StudentListActivity;
import com.infix.edu.activity.StudentSearchActivity;
import com.infix.edu.activity.TeacherStudentActivity;
import com.infix.edu.activity.TransportActivity;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder>{

    private Activity activity;
    private String[] name;
    private ArrayList<Integer> image;
    private int row_index = -1;
    private Context ctx;

    public AdminAdapter(Activity activity, String[] name, ArrayList<Integer> image, Context ctx) {
        this.activity = activity;
        this.name = name;
        this.image = image;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.row_layout_home, parent, false);

        return new AdminAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.imageView.setImageResource(image.get(position));
        holder.textView_Name.setText(name[position]);



        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.mipmap.card_select_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.white));
            holder.imageView.setColorFilter(activity.getResources().getColor(R.color.white));
        } else {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.mipmap.bg));
            holder.textView_Name.setTextColor(Color.GRAY);
            holder.imageView.setColorFilter(0);
        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index = position;
                notifyDataSetChanged();
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) ctx);

                switch (name[position]) {

                    case "Leave":
                        Intent adminLeave = new Intent(activity, AdminLeaveActivity.class);
                        activity.startActivity(adminLeave, options.toBundle());
                        break;

                    case "Students":
                        Intent student = new Intent(activity, StudentListActivity.class);
                        student.putExtra("status","admin");
                        activity.startActivity(student, options.toBundle());
                        break;
                    case "Dormitory":
                        Intent dormitory = new Intent(activity, AdminDormitoryDashActivity.class);
                        activity.startActivity(dormitory, options.toBundle());
                        break;
                    case "Room List":
                        Intent adddormitory = new Intent(activity, AdminDormitoryActivity.class);
                        activity.startActivity(adddormitory, options.toBundle());
                        break;
                    case "Add Room":
                        Intent add_room = new Intent(activity, AddDormitoryRoomActivity.class);
                        activity.startActivity(add_room, options.toBundle());
                        break;
                    case "Add Dormitory":
                        Intent add_dormitory = new Intent(activity, AddDormitoryActivity.class);
                        activity.startActivity(add_dormitory, options.toBundle());
                        break;
                    case "Transport":
                        Intent transport = new Intent(activity, AdminTransportActivity.class);
                        activity.startActivity(transport, options.toBundle());
                        break;
                    case "Add Route":
                        Intent route = new Intent(activity, AddRouteActivity.class);
                        activity.startActivity(route, options.toBundle());
                        break;
                    case "Add Vehicle":
                        Intent vehicle = new Intent(activity, AddVehicleActivity.class);
                        activity.startActivity(vehicle, options.toBundle());
                        break;
                    case "Transport List":
                        Intent transport_list = new Intent(activity, TransportActivity.class);
                        activity.startActivity(transport_list, options.toBundle());
                        break;
                    case "Staff":
                        Intent teacher = new Intent(activity, AdminStaffListActivity.class);
                        activity.startActivity(teacher, options.toBundle());
                        break;
                    case "Attendance":
                        Intent attendance = new Intent(activity, StudentSearchActivity.class);
                        attendance.putExtra("status","admin_attendance");
                        activity.startActivity(attendance, options.toBundle());
                        break;
                    case "Library":
                        Intent library = new Intent(activity, LibraryAdminActivity.class);
                        library.putExtra("status","admin_attendance");
                        activity.startActivity(library, options.toBundle());
                        break;
                    case "Book List":
                        Intent libraryList = new Intent(activity, LibraryActivity.class);
                        activity.startActivity(libraryList, options.toBundle());
                        break;
                    case "Add Books":
                        Intent addbook = new Intent(activity, AddBookActivity.class);
                        activity.startActivity(addbook, options.toBundle());
                        break;
                    case "Fees":
                        Intent fees = new Intent(activity, FeesAdminActivity.class);
                        activity.startActivity(fees, options.toBundle());
                        break;

                    case "Fees List":
                        Intent feeslist = new Intent(activity, FeesListActivity.class);
                        activity.startActivity(feeslist, options.toBundle());
                        break;
                    case "Add fees":
                        Intent feesadd = new Intent(activity, AddFeesActivity.class);
                        activity.startActivity(feesadd, options.toBundle());
                        break;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return image.size();
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
