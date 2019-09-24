package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.adapter.HomeWorkAdapter;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.HomeWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeWorkActivity extends AppCompatActivity {

    private ArrayList<HomeWork> homeWorks = new ArrayList<>();
    private RecyclerView recyclerView;
    private HomeWorkAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private int rule_id;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String profile_image_url;
    private CircleImageView profile;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        recyclerView = findViewById(R.id.homeWOrkRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Homework");

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            id = sharedPreferences.getInt("id",0);
            rule_id = sharedPreferences.getInt("role", 0);
        }

        if(rule_id == 4){
            url = MyConfig.getHomeWorkListUrl(id);
        }else{
            url = MyConfig.getzhomeWorksUrl(id);
        }

        getAllHomework(url);
        MyConfig.getProfileImage(profile_image_url,profile,HomeWorkActivity.this);


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

    void getAllHomework(String url){

        homeWorks.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");



                        for(int i = 0 ; i < array.length() ; i++){

                            String subject = array.getJSONObject(i).getString("subject_name");
                            String description = array.getJSONObject(i).getString("description");
                            String homeWorkDate = array.getJSONObject(i).getString("homework_date");
                            String submission_date = array.getJSONObject(i).getString("submission_date");
                            String evaluation_date = array.getJSONObject(i).getString("evaluation_date");
                            String file = array.getJSONObject(i).getString("file");
                            String status = array.getJSONObject(i).getString("status");
                            String marks = array.getJSONObject(i).getString("marks");

                            HomeWork homeWork = new HomeWork(homeWorkDate,submission_date,evaluation_date,file,marks,
                                    status,subject,description);
                            homeWorks.add(homeWork);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(homeWorks.size() > 0){

                    adapter = new HomeWorkAdapter(homeWorks,HomeWorkActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }


}
