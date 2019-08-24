package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.HomeAdapterHome;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Drawable[] img;
    private String[] names;
    private RecyclerView recycler;
    private StaggeredGridLayoutManager gridLayoutManager;
    private int role_id;
    private int id;
    private String email;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recycler = findViewById(R.id.home_recycler);

        gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        role_id = sharedPreferences.getInt("role", 0);
        id = sharedPreferences.getInt("id", 0);
        email = sharedPreferences.getString("email", null);
        name = sharedPreferences.getString("name", null);

        getFunctionality(role_id);

    }

    private void getFunctionality(int role) {

        ArrayList<Integer> images = new ArrayList<>();
        images.clear();

        switch (role){
            case 1:
                names = getResources().getStringArray(R.array.admins_functions_name);
                break;
            case 2:
                names = getResources().getStringArray(R.array.students_functions_name);
                break;
            case 3:
                names = getResources().getStringArray(R.array.parents_functions_name);
                break;
                case 4:
                    names = getResources().getStringArray(R.array.teachers_functions_name);
                break;

                default:
                    //names = getResources().getStringArray(R.array.teachers_functions_name);
                    break;

        }

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



    @Override
    public void onBackPressed() {

        finish();

    }

}
