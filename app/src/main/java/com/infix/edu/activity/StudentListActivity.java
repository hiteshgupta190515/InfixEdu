package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infix.edu.adapter.OptionAdapter;
import com.infix.edu.fragment.StudentFragment;
import com.infix.edu.R;
import com.infix.edu.myconfig.MyConfig;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentListActivity extends AppCompatActivity{

    private Fragment mainFragment;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    private TextView txt_search;
    private String status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        mainFragment = new StudentFragment();
        replaceFragment(mainFragment);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Student List");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        txt_search = findViewById(R.id.s_search);
        status = getIntent().getStringExtra("status");

        MyConfig.getProfileImage(profile_image_url, profile, StudentListActivity.this);


        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentListActivity.this,StudentSearchActivity.class);
                intent.putExtra("status",status);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(StudentListActivity.this).toBundle());
            }
        });

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

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.student_container,fragment);
        transaction.commit();
    }

}
