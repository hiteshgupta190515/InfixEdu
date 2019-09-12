package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.infix.edu.adapter.ClassExamScheduleAdapter;
import com.infix.edu.model.ExamSchedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassExamScheduleActivity extends AppCompatActivity {

    private ArrayList<ExamSchedule> schedules = new ArrayList<>();
    private RecyclerView recyclerView;
    private ClassExamScheduleAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private Toolbar toolbar;
    private TextView txtToolbarText;

    private ArrayList<String> examNames = new ArrayList<>();
    private ArrayList<Integer> examCodes = new ArrayList<>();
    private Spinner spnSearchExam;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_exam_schedule);

        recyclerView = findViewById(R.id.classExamScheduleRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        spnSearchExam = findViewById(R.id.class_schidule_spinner);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            id = sharedPreferences.getInt("id",0);
        }

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Schedule");
        MyConfig.getProfileImage(profile_image_url,profile,ClassExamScheduleActivity.this);


        getAllClassExamName(id);

    }

    void getAllClassExamName(final int id){

        examNames.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentClassExamName(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");


                        examNames.add("Select Exam");
                        examCodes.add(0);

                        for(int i = 0 ; i < array.length() ; i++){

                            String exam_name = array.getJSONObject(i).getString("exam_name");
                            int code = array.getJSONObject(i).getInt("exam_id");

                            examNames.add(exam_name);
                            examCodes.add(code);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(examNames.size() > 0){

                    ArrayAdapter adapter1 = new ArrayAdapter(ClassExamScheduleActivity.this,R.layout.spinner_row_layout,R.id.spn_text,examNames);
                    spnSearchExam.setAdapter(adapter1);

                    spnSearchExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(examCodes.get(i) != 0){

                                getExamSchidule(id,examCodes.get(i));

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


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

    void getExamSchidule(int id,int code){

        schedules.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentClsExamShedule(id,code), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");



                        for(int i = 0 ; i < array.length() ; i++){

                            String exam_name = array.getJSONObject(i).getString("exam_name");
                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            String date = array.getJSONObject(i).getString("date");
                            String room_no = array.getJSONObject(i).getString("room_no");
                            String start_time = array.getJSONObject(i).getString("start_time");
                            String end_time = array.getJSONObject(i).getString("end_time");

                           ExamSchedule schedule = new ExamSchedule(exam_name,subject_name,date,room_no,start_time,end_time);
                           schedules.add(schedule);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(schedules.size() > 0){

                    adapter = new ClassExamScheduleAdapter(schedules, ClassExamScheduleActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getApplicationContext(),"no schedule found!", Toast.LENGTH_SHORT).show();
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
