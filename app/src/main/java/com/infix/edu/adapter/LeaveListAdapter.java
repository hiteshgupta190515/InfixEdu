package com.infix.edu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.LeaveList;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

public class LeaveListAdapter extends RecyclerView.Adapter<LeaveListAdapter.LeaveViewHolder>{

    private ArrayList<LeaveList> leaveLists;
    private Context context;
    private  String strRadio;
    private int last_position = -1;

    public LeaveListAdapter(ArrayList<LeaveList> leaveLists, Context context) {
        this.leaveLists = leaveLists;
        this.context = context;
    }

    @NonNull
    @Override
    public LeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_list_row_layout,parent,false);

        return new LeaveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveViewHolder holder, final int position) {

        holder.txtFrom.setText(leaveLists.get(position).getFrom());
        holder.txtTo.setText(leaveLists.get(position).getTo());
        holder.txtApply.setText(leaveLists.get(position).getApply());
        holder.txtTitle.setText(leaveLists.get(position).getTitle());
        if(leaveLists.get(position).getStatus().equalsIgnoreCase("P")) {
            holder.txtStatus.setText(R.string.pending);
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.yellow));
        }else if(leaveLists.get(position).getStatus().equalsIgnoreCase("A")) {
            holder.txtStatus.setText(R.string.approved);
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.txtStatus.setText(R.string.rejected);
            holder.txtStatus.setBackgroundColor(Color.RED);
        }

        set_animation(holder.mView,position);

        if(leaveLists.get(position).getRole_id() == 1){

            holder.txtView.setVisibility(View.VISIBLE);

            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context,R.style.DialogTheme);
                    View mView = LayoutInflater.from(context).inflate(R.layout.leave_details_layout, null);
                    final Helper helper = new Helper();


                    TextView txtReason = mView.findViewById(R.id.txt_reason);
                    TextView txtLeaveType = mView.findViewById(R.id.txt_leave_type);
                    TextView txtLeaveFrom = mView.findViewById(R.id.txt_leave_from);
                    TextView txtLeaveTo = mView.findViewById(R.id.txt_leave_to);
                    TextView txtApplyDate = mView.findViewById(R.id.txt_apply_date);
                    RadioGroup radioGroup = mView.findViewById(R.id.radio_group);
                    RadioButton rbPending = mView.findViewById(R.id.pending);
                    RadioButton rbCancel = mView.findViewById(R.id.cancel);
                    RadioButton rbApproved = mView.findViewById(R.id.approved);
                    Button button = mView.findViewById(R.id.btn_save_leave_status);


                    if(leaveLists.get(position).getStatus().equalsIgnoreCase("P")){

                        rbPending.setChecked(true);
                        strRadio = "P";

                    }else if(leaveLists.get(position).getStatus().equalsIgnoreCase("A")){

                        strRadio = "A";
                        rbApproved.setChecked(true);
                    }
                    else {
                        rbCancel.setChecked(true);
                        strRadio = "R";
                    }


                    txtReason.setText(leaveLists.get(position).getReason());
                    txtLeaveType.setText(leaveLists.get(position).getTitle());
                    txtLeaveFrom.setText(leaveLists.get(position).getFrom());
                    txtLeaveTo.setText(leaveLists.get(position).getTo());
                    txtApplyDate.setText(leaveLists.get(position).getApply());


                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            switch (checkedId) {

                                case R.id.pending:
                                    strRadio  = "P";
                                    break;

                                case R.id.approved:
                                    strRadio  = "A";
                                    break;

                                case R.id.cancel:
                                    strRadio  = "R";
                                    break;

                            }
                        }

                    });

                    alertBuilder.setView(mView);
                    final AlertDialog dialog = alertBuilder.create();

                    Rect displayRectangle = new Rect();
                    Window window = dialog.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                    mView.setMinimumWidth((int)(displayRectangle.width()));
                    mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           helper.setLeaveStatus(leaveLists.get(position).getId(),strRadio,context);

                                if(!strRadio.equals(leaveLists.get(position).getStatus()))
                                leaveLists.remove(position);
                                notifyDataSetChanged();
                                dialog.dismiss();


                        }
                    });

                }
            });

        }

    }

    public void set_animation(View v,int position){

        if (position > last_position){

            //Load the animation from the xml file and set it to the row
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_left_anim);
            animation.setDuration(500);
            v.startAnimation(animation);
            last_position = position;
        }

    }

    @Override
    public int getItemCount() {
        return leaveLists.size();
    }

    public static class LeaveViewHolder extends RecyclerView.ViewHolder{

        TextView txtFrom;
        TextView txtTo;
        TextView txtApply;
        TextView txtStatus;
        TextView txtView;
        TextView txtTitle;
        View mView;

        public LeaveViewHolder(@NonNull View v) {
            super(v);

            mView = v;

         txtFrom = v.findViewById(R.id.from_date);
         txtTo = v.findViewById(R.id.to_date);
         txtApply = v.findViewById(R.id.apply_date);
         txtStatus = v.findViewById(R.id.status);
         txtView = v.findViewById(R.id.txtLeaveView);
         txtTitle = v.findViewById(R.id.txtLeaveTitle);

        }
    }

}
