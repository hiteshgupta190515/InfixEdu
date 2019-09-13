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
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.adapter.OnlineExamAdapter;
import com.infix.edu.model.OnlineExam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveOnlineExamActivity extends AppCompatActivity {

    private ArrayList<OnlineExam> onlineExams = new ArrayList<>();
    private RecyclerView recyclerView;
    private OnlineExamAdapter adapter;

    private Toolbar toolbar;
    private TextView txtToolbarText;

    private SharedPreferences sharedPreferences;
    private int id;

    private String profile_image_url;
    private CircleImageView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_online_exam);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            id = sharedPreferences.getInt("id",0);
        };

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Active Exams");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);


        recyclerView = findViewById(R.id.activeOnlineExamRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllOnlineExam(id);
        MyConfig.getProfileImage(profile_image_url,profile,ActiveOnlineExamActivity.this);

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

    void getAllOnlineExam(int id){

        onlineExams.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getOnlineExamNameUrl(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("online_exams");



                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("exam_title");
                            String date = array.getJSONObject(i).getString("date");
                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            String onlineExamStatus = array.getJSONObject(i).getString("onlineExamStatus");
                            int onlineExamTakeStatus = array.getJSONObject(i).getInt("onlineExamTakeStatus");

                            OnlineExam onlineExam = new OnlineExam(title,subject_name,date,onlineExamTakeStatus);
                            onlineExams.add(onlineExam);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(onlineExams.size() > 0){

                    adapter = new OnlineExamAdapter(onlineExams);
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
