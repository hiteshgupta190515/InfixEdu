package com.infix.edu.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.HomeWork;

import java.util.ArrayList;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.MViewHolder>{

    private ArrayList<HomeWork> homeWorks;
    private Context ctx;
    private boolean isGranted;

    public HomeWorkAdapter(ArrayList<HomeWork> homeWorks, Context ctx) {
        this.homeWorks = homeWorks;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_row_layout,parent,false);

        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MViewHolder holder, final int position) {

        holder.txtTitle.setText(homeWorks.get(position).getSubject());
        holder.txtCreated.setText(homeWorks.get(position).getHomeworkDate());
        holder.txtSubmission.setText(homeWorks.get(position).getSubmissionDate());
        holder.txtEvaluation.setText(homeWorks.get(position).getEvaluationdate());

        if(homeWorks.get(position).getStatus().equalsIgnoreCase("c")) {
            holder.txtStatus.setText("completed");
            holder.txtStatus.setBackgroundColor(ctx.getResources().getColor(R.color.green));
        }else {
            holder.txtStatus.setText("not complete");
            holder.txtStatus.setBackgroundColor(Color.RED);
        }

        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx,R.style.DialogTheme);
                View mView = LayoutInflater.from(ctx).inflate(R.layout.homework_details, null);

                TextView created = mView.findViewById(R.id.dcreated);
                TextView submission = mView.findViewById(R.id.dsubmission);
                TextView evaluation = mView.findViewById(R.id.devaluation);
                TextView status = mView.findViewById(R.id.dstatus);
                TextView txtHWTitle = mView.findViewById(R.id.txtHWTitle);


                created.setText(homeWorks.get(position).getHomeworkDate());
                submission.setText("$"+homeWorks.get(position).getSubmissionDate());
                evaluation.setText("$"+homeWorks.get(position).getEvaluationdate());
                txtHWTitle.setText(homeWorks.get(position).getTitle());

                if(homeWorks.get(position).getStatus().equalsIgnoreCase("c")) {
                    status.setText("completed");
                    status.setBackgroundColor(ctx.getResources().getColor(R.color.green));
                }else {
                    status.setText("not complete");
                    status.setBackgroundColor(Color.RED);
                }


                alertBuilder.setView(mView);
                AlertDialog dialog = alertBuilder.create();

                Rect displayRectangle = new Rect();
                Window window = dialog.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

                mView.setMinimumWidth((int)(displayRectangle.width()));
                mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();


            }
        });

        holder.txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

                    askForPermission();

                    if(isGranted){

                        final String file  = homeWorks.get(position).getFile();


                        if(file != "null"){

                            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                            builder.setMessage("Do you want to download this file?");

                            builder.setPositiveButton("download", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    holder.downloadFile(MyConfig.ROOT_URL+file,homeWorks.get(position).getTitle(),homeWorks.get(position).getSubject(),ctx);
                                }
                            }).setNegativeButton("no",null);

                            Dialog dialog = builder.create();
                            dialog.show();

                        }else{
                            Toast.makeText(ctx,"file not found",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            isGranted = true;

        }
        ActivityCompat.requestPermissions((Activity) ctx, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
    }

    @Override
    public int getItemCount() {
        return homeWorks.size();
    }

    public static class MViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDownload;
        TextView txtView;
        TextView txtCreated;
        TextView txtSubmission;
        TextView txtEvaluation;
        TextView txtStatus;

        public MViewHolder(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.txtHWTitle);
            txtDownload = v.findViewById(R.id.txtDownload);
            txtView = v.findViewById(R.id.txtHWClassView);
            txtCreated = v.findViewById(R.id.created);
            txtStatus = v.findViewById(R.id.status);
            txtSubmission = v.findViewById(R.id.submission);
            txtEvaluation = v.findViewById(R.id.evaluation);
        }

        void downloadFile(String _url,String title,String subject,Context ctx) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(_url));
            request.setDescription(title);
            request.setTitle(subject);
// in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "data/");

// get download service and enqueue file
            DownloadManager manager = (DownloadManager)ctx.getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }

    }

}
