package com.infix.edu.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.edu.R;
import com.infix.edu.model.Content;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

public class ContentListAdapter extends RecyclerView.Adapter<ContentListAdapter.ContentView>{

    private ArrayList<Content> contents;
    private Context ctx;
    private String type;
    private boolean isGranted;

    public ContentListAdapter(ArrayList<Content> contents, Context ctx) {
        this.contents = contents;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ContentView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_row_layout,parent,false);

        return new ContentView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContentView holder, final int position) {

        holder.txt_title.setText(contents.get(position).getTitle());

        switch (contents.get(position).getType()){

            case "as":
                type = "assignment";
                break;
            case "st":
                type = "study material";
                break;
            case "sy":
                type = "syllabus";
                break;
            case "ot":
                type = "other download";
                break;

        }

        holder.txt_type.setText(type);
        holder.txt_date.setText(contents.get(position).getDate());
        holder.txt_class.setText(contents.get(position).getClasses());

        holder.txt_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askForPermission();

                if(isGranted){

                    final String file  = contents.get(position).getUrl();

                    if(file != "null"){

                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                        builder.setMessage("Do you want to download this file?");

                        builder.setPositiveButton("download", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                holder.downloadFile(MyConfig.ROOT_URL+file,contents.get(position).getTitle(),contents.get(position).getType(),ctx);
                            }
                        }).setNegativeButton("no",null);

                        Dialog dialog = builder.create();
                        dialog.show();

                    }else{
                        Toast.makeText(ctx,"file not found",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        holder.txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(contents.get(position).getId() != 0){

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setMessage("Do you want to delete this content?");

                    builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyConfig.deleteContent(contents.get(position).getId());
                        }
                    }).setNegativeButton("no",null);

                    Dialog dialog = builder.create();
                    dialog.show();

                }else{
                    Toast.makeText(ctx,"file not found",Toast.LENGTH_SHORT).show();
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
        return contents.size();
    }

    public static class ContentView extends RecyclerView.ViewHolder{

        TextView txt_title;
        TextView txt_type;
        TextView txt_date;
        TextView txt_class;
        TextView txt_delete;
        TextView txt_download;

        public ContentView(@NonNull View v) {
            super(v);

            txt_class = v.findViewById(R.id.mClass);
            txt_date = v.findViewById(R.id.date);
            txt_delete = v.findViewById(R.id.delete);
            txt_download = v.findViewById(R.id.txtDownload);
            txt_title = v.findViewById(R.id.txtTitle);
            txt_type = v.findViewById(R.id.type);

            txt_download.getPaint().setUnderlineText(true);
            txt_delete.getPaint().setUnderlineText(true);
        }

        void downloadFile(String _url,String title,String subject,Context ctx) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(_url));
            request.setDescription(title);
            request.setTitle(title+subject);
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
