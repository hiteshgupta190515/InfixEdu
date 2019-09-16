package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.infix.edu.R;
import com.infix.edu.adapter.TeacherHomeAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAcademicsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private GridLayoutManager gridLayoutManager;
    private SharedPreferences sharedPreferences;
    private int role_id;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private CircleImageView profile;
    private String[] names;
    private ArrayList<Integer> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_academics);

        recycler = findViewById(R.id.teacher_academics_recycler);
        profile = findViewById(R.id.profile);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Academics");

        gridLayoutManager = new GridLayoutManager(TeacherAcademicsActivity.this,4);
        recycler.setLayoutManager(gridLayoutManager);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        role_id = sharedPreferences.getInt("role", 0);

        names = getResources().getStringArray(R.array.teachers_academics_functions_name);

        for(String n : names){

            images.add(imageSearch(n.toLowerCase().replace(" ","")));
        }

        TeacherHomeAdapter teacherHomeAdapter = new TeacherHomeAdapter(this,names,images,this);
        recycler.setAdapter(teacherHomeAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int imageSearch(String text) {

        int img = 0;
        try{

            img = getResources().getIdentifier(text, "mipmap", getPackageName());

            if(img == 0){
                img = getResources().getIdentifier("inventory", "mipmap", getPackageName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return img;
    }

}
