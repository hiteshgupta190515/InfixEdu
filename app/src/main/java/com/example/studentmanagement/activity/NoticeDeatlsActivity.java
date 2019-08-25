package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.studentmanagement.R;

public class NoticeDeatlsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String title,date,description;
    private TextView txtTitle,txtDate,txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_deatls);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

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

    }
}
