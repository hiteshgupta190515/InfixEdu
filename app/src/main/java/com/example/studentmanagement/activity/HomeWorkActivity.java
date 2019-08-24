package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.ComplaintAdapter;
import com.example.studentmanagement.adapter.HomeAdapterHome;
import com.example.studentmanagement.adapter.HomeWorkAdapter;
import com.example.studentmanagement.model.Complaint;
import com.example.studentmanagement.model.HomeWork;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeWorkActivity extends AppCompatActivity {

    private ArrayList<HomeWork> homeWorks = new ArrayList<>();
    private RecyclerView recyclerView;
    private HomeWorkAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private Toolbar toolbar;
    private TextView txtToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        recyclerView = findViewById(R.id.homeWOrkRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Homework");

        getAllHomework(id);


    }


    void getAllHomework(int id){

        homeWorks.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getzhomeWorksUrl(id), null, new Response.Listener<JSONObject>() {
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