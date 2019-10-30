package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.adapter.StudentListAdapter;
import com.infix.edu.model.Tstudent;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeacherStudentActivity extends AppCompatActivity{

    private RecyclerView studentRecyclerView;
    private StudentListAdapter adapter;
    private ArrayList<Tstudent> students = new ArrayList<>();
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String url;
    private String status;
    private String date;
    private Button btnSave;
    private LinearLayout linearLayout;
    private RadioGroup radioGroup;
    public static String selected_radio_btn;
    private RadioButton rdPresent;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        btnSave = findViewById(R.id.btn_attendance_save);
        linearLayout = findViewById(R.id.attendance_linear_layout);
        radioGroup = findViewById(R.id.attendance_radio_group);
        rdPresent = findViewById(R.id.present);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Student List");
        selected_radio_btn = "P";
        rdPresent.setChecked(true);

        helper = new Helper();


        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        studentRecyclerView = findViewById(R.id.studentRecycler);
        studentRecyclerView.setHasFixedSize(true);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int class_id = getIntent().getIntExtra("classid",0);
        int section_id = getIntent().getIntExtra("sectionid",0);
        String name = getIntent().getStringExtra("name").trim();
        String roll = getIntent().getStringExtra("roll").trim();
        status = getIntent().getStringExtra("status");
        date = getIntent().getStringExtra("date");

        if(status.equalsIgnoreCase("admin_attendance")){

            btnSave.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.VISIBLE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for(int i = 0 ; i < StudentListAdapter.ids.size() ; i++){

                        final String id = StudentListAdapter.ids.get(i);
                        final String attn = StudentListAdapter.abs.get(i);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                helper.setAttendanceData(id,attn,date,getApplicationContext());
                            }
                        },500);

                    }

                }
            });
        }

        if(!roll.equals("")){
            url = MyConfig.getStudentByRoll(roll);
            getAllStudentByNameAndRoll(url);
        }else if(!name.equals("")){
            url = MyConfig.getStudentByName(name);
            getAllStudentByNameAndRoll(url);
        }else if(class_id != 0){
            if(section_id != 0){
                url = MyConfig.getStudentByClassAndSection(class_id,section_id);
                getAllStudentByClass(url);
            }else{
                url = MyConfig.getStudentByClass(class_id);
                getAllStudentByClass(url);
            }
        }else{
            Toast.makeText(getApplicationContext(),"student does not found!",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){

                    case R.id.present:
                        selected_radio_btn = "P";
                        break;
                    case R.id.absent:
                        selected_radio_btn = "A";
                        break;
                    case R.id.late:
                        selected_radio_btn = "L";
                        break;
                    case R.id.half_day:
                        selected_radio_btn = "H";
                        break;
                }
            }
        });


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

    public void getAllStudentByClass(String url){

        students.clear();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
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
                            adapter = new StudentListAdapter(students,TeacherStudentActivity.this,status);
                            studentRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility(View.GONE);
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

    public void getAllStudentByNameAndRoll(String url){

        students.clear();

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.getBoolean("success")){


                                JSONObject json= response.getJSONObject("data").getJSONObject("students");


                                    String full_name = json.getString("full_name");
                                    String student_photo = json.getString("student_photo");
                                    int roll_no = json.getInt("roll_no");
                                    String class_name = json.getString("class_name");
                                    String section_name = json.getString("section_name");
                                    int user_id = json.getInt("user_id");

                                    Tstudent tstudent = new Tstudent(full_name,roll_no,class_name,section_name,student_photo,user_id);
                                    students.add(tstudent);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(students.size() > 0){

                            adapter = new StudentListAdapter(students,TeacherStudentActivity.this,status);
                            studentRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        progressBar.setVisibility(View.GONE);

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
