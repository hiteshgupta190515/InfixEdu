package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.infix.edu.R;
import com.infix.edu.adapter.AdminAdapter;
import com.infix.edu.adapter.DormitoryAdapter;
import com.infix.edu.model.Dormitory;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminTransportActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_admin_transport);


        images = new ArrayList<>();
        images.clear();

        recycler = findViewById(R.id.admin_transport_list);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Transport");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);


        gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);

        MyConfig.getProfileImage(profile_image_url,profile,AdminTransportActivity.this);

        names = getResources().getStringArray(R.array.admin_transport_option);

        for(String n : names){

            images.add(imageSearch(n.toLowerCase().replace(" ","")));
        }

        AdminAdapter adminAdapter = new AdminAdapter(this,names,images,this);
        recycler.setAdapter(adminAdapter);


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
