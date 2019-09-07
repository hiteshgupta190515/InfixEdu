package com.infix.studentmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.activity.NoticeDeatlsActivity;
import com.infix.studentmanagement.model.Notice;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{

    private ArrayList<Notice> notices;
    private Context ctx;

    public NoticeAdapter(ArrayList<Notice> notices, Context ctx) {
        this.notices = notices;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_row_layout,parent,false);

        return new NoticeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, final int position) {

        holder.txtTitle.setText(notices.get(position).getTitle());
        holder.txtDate.setText(notices.get(position).getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, NoticeDeatlsActivity.class);
                intent.putExtra("title",notices.get(position).getTitle());
                intent.putExtra("date",notices.get(position).getDate());
                intent.putExtra("description",notices.get(position).getDetails());

                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDate;
        LinearLayout linearLayout;

        public NoticeViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtNoticeTitle);
            txtDate = v.findViewById(R.id.txtDate);
            linearLayout = v.findViewById(R.id.noticeClick);

        }
    }

}
