package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentmanagement.R;

import io.armcha.elasticview.ElasticView;

public class AdminSectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ElasticView elVisitor;
    private ElasticView elVComplaint;
    private ElasticView elVPostal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_section);

        elVisitor = findViewById(R.id.visitor);
        elVComplaint = findViewById(R.id.complaint);
        elVisitor.setOnClickListener(this);
        elVComplaint.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.visitor:
                startActivity(new Intent(this,VisitorActivity.class));
                break;
            case R.id.complaint:
                startActivity(new Intent(this,ComplaintActivity.class));
                break;
                default:
                    break;

        }

    }
}
