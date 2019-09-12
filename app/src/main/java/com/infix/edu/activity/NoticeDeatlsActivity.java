package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoticeDeatlsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String title,date,description;
    private TextView txtTitle,txtDate,txtDescription;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_deatls);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Back");

        txtTitle = findViewById(R.id.txtNoticeTitle);
        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.details);

        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");

        txtTitle.setText(title);
        txtDate.setText(date);
        txtDescription.setText(description);

        MyConfig.getProfileImage(profile_image_url,profile,NoticeDeatlsActivity.this);

    }
}
