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
import com.example.studentmanagement.adapter.BookIssuedAdapter;
import com.example.studentmanagement.adapter.DormitoryAdapter;
import com.example.studentmanagement.model.BookIssue;
import com.example.studentmanagement.model.Dormitory;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DormitoryActivity extends AppCompatActivity {

    private ArrayList<Dormitory> dormitories = new ArrayList<>();
    private RecyclerView recyclerView;
    private DormitoryAdapter adapter;

    private Toolbar toolbar;
    private TextView txtToolbarText;

    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormitory);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Dormitory");


        //main recyclerview
        recyclerView = findViewById(R.id.dormitoryRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllDormitories();

    }

    void getAllDormitories(){

        dormitories.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.STUDENT_DORMITORY_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");



                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("dormitory_name");
                            String room = array.getJSONObject(i).getString("room_number");
                            int bed = array.getJSONObject(i).getInt("number_of_bed");
                            String cost = array.getJSONObject(i).getString("cost_per_bed");
                            int status = array.getJSONObject(i).getInt("active_status");

                            Dormitory dormitory = new Dormitory(title,room,bed,cost,status);

                            dormitories.add(dormitory);


                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(dormitories.size() > 0){

                    adapter = new DormitoryAdapter(dormitories,getApplicationContext());
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
