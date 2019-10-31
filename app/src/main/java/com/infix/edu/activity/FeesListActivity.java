package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.infix.edu.R;
import com.infix.edu.adapter.FeesAdminAdapter;
import com.infix.edu.model.Child;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeesListActivity extends AppCompatActivity {

    private ArrayList<SearchData> fees_list = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeesAdminAdapter adapter;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private CircleImageView profile;
    private String profile_image_url;
    private SharedPreferences sharedPreferences;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_list);

        helper = new Helper();
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        recyclerView = findViewById(R.id.fees_group_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Fees List");
        profile = findViewById(R.id.profile);

        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,FeesListActivity.this);

        fees_list = helper.getAllFeesList(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new FeesAdminAdapter(fees_list,FeesListActivity.this);
                recyclerView.setAdapter(adapter);
            }
        },2000);

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

}
