package com.example.studentmanagement.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.activity.AdminSectionActivity;
import com.example.studentmanagement.activity.InventoryActivity;
import com.example.studentmanagement.activity.LibraryActivity;
import com.example.studentmanagement.activity.StudentListActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeAdapterHome extends RecyclerView.Adapter<HomeAdapterHome.ViewHolder> {

    private Activity activity;
    private String[] name;
    private ArrayList<Integer> image;
    private int row_index = -1;


    public HomeAdapterHome(Activity activity, String[] name, ArrayList<Integer> image) {
        this.activity = activity;
        this.name = name;
        this.image = image;

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
                        activity.startActivity(new Intent(activity, LibraryActivity.class));
                        break;
                    case "Inventory":
                        activity.startActivity(new Intent(activity, InventoryActivity.class));
                        break;
                    case "Admin Section":
                        activity.startActivity(new Intent(activity, AdminSectionActivity.class));
                        break;
                    default:
                        break;

                }

            }
        });

        if (row_index == position) {
            holder.linearLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.draware_selected_background));
            holder.textView_Name.setTextColor(activity.getResources().getColor(R.color.drawar_text_select));
            //  holder.imageView.setColorFilter(activity.getResources().getColor(R.color.drawar_image_select));
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

        private CircleImageView imageView;
        private TextView textView_Name;
        private LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (CircleImageView) itemView.findViewById(R.id.home_imageview);
            textView_Name = (TextView) itemView.findViewById(R.id.textView_home_adapter);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_home_adapter);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}
