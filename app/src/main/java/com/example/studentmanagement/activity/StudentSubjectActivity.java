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
import com.example.studentmanagement.adapter.NoticeAdapter;
import com.example.studentmanagement.adapter.SubjectAdapter;
import com.example.studentmanagement.model.Notice;
import com.example.studentmanagement.model.Subject;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentSubjectActivity extends AppCompatActivity {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private int id;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private TextView txtToolbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject);

        recyclerView = findViewById(R.id.subjectRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Subjects");
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);


        getAllSUbject(id);


    }

    void getAllSUbject(int id){

        subjects.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getSubjectsUrl(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){


                        JSONArray array = response.getJSONObject("data").getJSONArray("student_subjects");



                        for(int i = 0 ; i < array.length() ; i++){


                            String teacher_name = array.getJSONObject(i).getString("teacher_name");
                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            String subject_type = array.getJSONObject(i).getString("subject_type");
                            String subject_code = array.getJSONObject(i).getString("subject_code");

                            Subject subject = new Subject(subject_name,teacher_name,subject_type,subject_code);

                            subjects.add(subject);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(subjects.size() > 0){

                    adapter = new SubjectAdapter(subjects, StudentSubjectActivity.this);
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
