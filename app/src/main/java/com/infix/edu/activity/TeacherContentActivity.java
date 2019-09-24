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

import com.infix.edu.R;
import com.infix.edu.adapter.TeacherHomeAdapter;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherContentActivity extends AppCompatActivity {

    private String[] names;
    private RecyclerView recycler;
    private StaggeredGridLayoutManager gridLayoutManager;
    private int id;
    ArrayList<Integer> images;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_content);



        images = new ArrayList<>();
        images.clear();

        recycler = findViewById(R.id.teacherContentRecycler);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Content");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);


        gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);

        MyConfig.getProfileImage(profile_image_url,profile,TeacherContentActivity.this);

        names = getResources().getStringArray(R.array.teachers_contents_functions_name);

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
