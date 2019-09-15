package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.adapter.StudentListAdapter;
import com.infix.edu.model.Tstudent;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeacherStudentActivity extends AppCompatActivity {

    private RecyclerView studentRecyclerView;
    private StudentListAdapter adapter;
    private ArrayList<Tstudent> students = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student);


        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        studentRecyclerView = findViewById(R.id.studentRecycler);
        studentRecyclerView.setHasFixedSize(true);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int class_id = getIntent().getIntExtra("classid",0);

        getAllStudent(class_id);

    }


    public void getAllStudent(int id){


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentByClass(id), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.getBoolean("success")){


                                JSONArray jsonArray = response.getJSONObject("data").getJSONArray("students");

                                for(int i = 0 ; i < jsonArray.length() ; i++){


                                    String full_name = jsonArray.getJSONObject(i).getString("full_name");
                                    String student_photo = jsonArray.getJSONObject(i).getString("student_photo");
                                    int roll_no = jsonArray.getJSONObject(i).getInt("roll_no");
                                    String class_name = jsonArray.getJSONObject(i).getString("class_name");
                                    String section_name = jsonArray.getJSONObject(i).getString("section_name");
                                    int user_id = jsonArray.getJSONObject(i).getInt("user_id");

                                    Tstudent tstudent = new Tstudent(full_name,roll_no,class_name,section_name,student_photo,user_id);
                                    students.add(tstudent);
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(students.size() > 0){

                            adapter = new StudentListAdapter(students,TeacherStudentActivity.this);
                            studentRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }

}
