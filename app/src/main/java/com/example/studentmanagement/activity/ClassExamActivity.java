package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.HomeAdapterHome;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassExamActivity extends AppCompatActivity {

    private String[] names;
    ArrayList<Integer> images;
    private RecyclerView recycler;
    private StaggeredGridLayoutManager gridLayoutManager;
    private int role_id;
    private int id;
    private String email;
    private String password;
    private String name;
    private CircleImageView profile;

    private Toolbar toolbar;
    private TextView txtToolbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_exam);

        images = new ArrayList<>();
        images.clear();

        recycler = findViewById(R.id.cls_exam_home_recycler);
        profile = findViewById(R.id.profile);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Examination");

        gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        role_id = sharedPreferences.getInt("role", 0);
        id = sharedPreferences.getInt("id", 0);
        email = sharedPreferences.getString("email", null);
        password = sharedPreferences.getString("password",null);
        name = sharedPreferences.getString("name", null);

        names = getResources().getStringArray(R.array.class_exam_student_functions_name);

        for(String n : names){

            images.add(imageSearch(n.toLowerCase().replace(" ","")));
        }

        HomeAdapterHome homeAdapterHome = new HomeAdapterHome(this,names,images,this);
        recycler.setAdapter(homeAdapterHome);


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
