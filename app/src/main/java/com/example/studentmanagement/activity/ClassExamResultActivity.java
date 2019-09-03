package com.example.studentmanagement.activity;

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
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.ResultAdapter;
import com.example.studentmanagement.model.Result;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassExamResultActivity extends AppCompatActivity {

    private ArrayList<Result> results = new ArrayList<>();
    private ArrayList<String> examNames = new ArrayList<>();
    private ArrayList<Integer> examCodes = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultAdapter adapter;

    private Spinner spnSearchExam;

    private Toolbar toolbar;
    private TextView txtToolbarText;

    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_exam_result);

        spnSearchExam = findViewById(R.id.choose_class_exam_spinner);


        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Choose Exam");


        recyclerView = findViewById(R.id.class_result_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

                    ArrayAdapter adapter1 = new ArrayAdapter(ClassExamResultActivity.this,R.layout.spinner_row_layout,R.id.spn_text,examNames);
                    spnSearchExam.setAdapter(adapter1);

                    spnSearchExam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(examCodes.get(i) != 0){

                                getExamResult(id,examCodes.get(i));

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

    void getExamResult(int id,int code){

        results.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentClassExamResult(id,code), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("exam_result");



                        for(int i = 0 ; i < array.length() ; i++){

                            String grade = array.getJSONObject(i).getString("grade");
                            String exam_name = array.getJSONObject(i).getString("exam_name");
                            int total_marks = array.getJSONObject(i).getInt("total_marks");
                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            int obtained_marks = array.getJSONObject(i).getInt("obtained_marks");
                            int pass_mark = response.getJSONObject("data").getInt("pass_marks");

                            Result result = new Result(grade,total_marks,subject_name,obtained_marks,pass_mark,exam_name);
                            results.add(result);
                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(results.size() > 0){

                    adapter = new ResultAdapter(results,ClassExamResultActivity.this);
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
