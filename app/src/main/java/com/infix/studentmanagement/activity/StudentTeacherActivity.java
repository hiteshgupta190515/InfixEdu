package com.infix.studentmanagement.activity;

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
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.adapter.SubjectAdapter;
import com.infix.studentmanagement.model.Subject;
import com.infix.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentTeacherActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_student_teacher);

        recyclerView = findViewById(R.id.teacherRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Teachers");
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);

        getAllTeachers(id);

    }

    void getAllTeachers(int id){

        subjects.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentTeacherUrl(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){


                        JSONArray array = response.getJSONObject("data").getJSONArray("teacher_list");



                        for(int i = 0 ; i < array.length() ; i++){


                            String teacher_name = array.getJSONObject(i).getString("full_name");
                            String email = array.getJSONObject(i).getString("email");
                            String phone = array.getJSONObject(i).getString("mobile");

                            Subject subject = new Subject(teacher_name,email,phone);

                            subjects.add(subject);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(subjects.size() > 0){

                    adapter = new SubjectAdapter(subjects, StudentTeacherActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getCause().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }

}
