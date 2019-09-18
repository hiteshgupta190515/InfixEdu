package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.adapter.SubjectAdapter;
import com.infix.edu.model.Subject;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherSubjectActivity extends AppCompatActivity {

    private ArrayList<Subject> subjects = new ArrayList<>();
    private RecyclerView recyclerView;
    private SubjectAdapter adapter;
    private int id;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_subject);

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
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        id = sharedPreferences.getInt("id",0);

        getAllSUbject(id);
        MyConfig.getProfileImage(profile_image_url,profile,TeacherSubjectActivity.this);
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

    void getAllSUbject(int id){

        subjects.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getTeacherSubject(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){


                        JSONArray array = response.getJSONObject("data").getJSONArray("subjectsName");



                        for(int i = 0 ; i < array.length() ; i++){


                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            String subject_code = array.getJSONObject(i).getString("subject_code");
                            String subject_type = array.getJSONObject(i).getString("subject_type");

                            Subject subject = new Subject(subject_name,null,subject_type,subject_code);

                            subjects.add(subject);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(subjects.size() > 0){

                    adapter = new SubjectAdapter(subjects, TeacherSubjectActivity.this);
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
