package com.infix.studentmanagement.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.activity.HomeActivity;
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

    @Override
    public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_row_layout,parent,false);

        return new ChildViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChildViewHolder holder, final int position) {

        String url = MyConfig.ROOT_URL+childs.get(position).getImgUrl();
        String name = childs.get(position).getName();
        String des = "Class "+childs.get(position).getmClass()+" | "+"Section "+childs.get(position).getSection()+" | Roll "+childs.get(position).getRoll();

        if(name != null && des != null){

            holder.txtName.setText(name);
            holder.txtDes.setText(des);

        }

        Log.d("image",url);

        holder.setImage(url);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(ctx, HomeActivity.class);
                intent.putExtra("rule_id",2);
                intent.putExtra("id",childs.get(position).getId());

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

        public void setImage(String url){
            try {

                Glide.with(ctx)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .fitCenter()
                        .into(image);
            } catch (Exception e){
            }
        }

    }

}
