package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.infix.edu.adapter.HomeAdapterHome;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;

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
    private SharedPreferences sharedPreferences;
    private String profile_image_url;


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

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Examination");
        profile_image_url = sharedPreferences.getString("profile_image",null);

        gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);

        //getting profile photo
        MyConfig.getProfileImage(profile_image_url,profile,ClassExamActivity.this);

        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            id = sharedPreferences.getInt("id",0);
        }

        names = getResources().getStringArray(R.array.class_exam_student_functions_name);

        for(String n : names){

            images.add(imageSearch(n.toLowerCase().replace(" ","")));
        }

        HomeAdapterHome homeAdapterHome = new HomeAdapterHome(this,names,images,id,this);
        recycler.setAdapter(homeAdapterHome);


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
