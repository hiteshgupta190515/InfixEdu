package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.infix.edu.model.SearchData;
import com.infix.edu.model.Subject;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddHomeWorkActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private Spinner spnClass;
    private Spinner spnSection;
    private Spinner spnSubject;
    private Button btnSave;
    private ArrayList<SearchData> classData = new ArrayList<>();
    private ArrayList<SearchData> sectionData = new ArrayList<>();
    private ArrayList<SearchData> subjectData = new ArrayList<>();
    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<String> sections = new ArrayList<>();
    private ArrayList<String> subjects = new ArrayList<>();
    private int class_id;
    private int section_id;
    private String class_name;
    private String section_name;
    private String subject_name;
    private int subject_id;
    private TextView txtBrowse;
    private EditText etDescription;
    private LinearLayout assign_date;
    private LinearLayout submission_date;
    private LinearLayout attach_file;

    private SharedPreferences sharedPreferences;
    private int id;
    private String profile_image_url;
    private CircleImageView profile;

    private int year, month, day;
    private DatePickerDialog datePickerDialog;
    private String mAssign_date,mSubmission_date;
    private TextView txtAssign,txtSubmission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);

        spnClass = findViewById(R.id.choose_class_spinner);
        spnSection = findViewById(R.id.choose_section_spinner);
        spnSubject = findViewById(R.id.choose_subject_spinner);
        btnSave = findViewById(R.id.btnSaveHomework);

        txtBrowse = findViewById(R.id.txtBrowse);
        txtBrowse.getPaint().setUnderlineText(true);

        etDescription = findViewById(R.id.comments_textbox);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        assign_date = findViewById(R.id.assign_date);
        submission_date = findViewById(R.id.submission_date);
        attach_file = findViewById(R.id.attach_file);
        assign_date.setOnClickListener(this);
        submission_date.setOnClickListener(this);
        attach_file.setOnClickListener(this);
        txtAssign = findViewById(R.id.choose_assign);
        txtSubmission = findViewById(R.id.choose_submission);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Homework");

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        id = sharedPreferences.getInt("id",0);
        MyConfig.getProfileImage(profile_image_url,profile,AddHomeWorkActivity.this);


        getClassAndSectionName();
        getAllSUbject(id);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = etDescription.getText().toString();

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

    void getClassAndSectionName(){

        classData.clear();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getClassAndSection(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONObject main = response.getJSONObject("data");
                        JSONArray classNameArray = main.getJSONArray("classes");
                        JSONArray sectionNameArray = main.getJSONArray("sections");

                        classes.add("select classes");
                        sections.add("select section");

                        for(int i = 0 ; i < classNameArray.length() ; i++){

                            String className = classNameArray.getJSONObject(i).getString("class_name");
                            classes.add(className);
                            int id = classNameArray.getJSONObject(i).getInt("id");
                            classData.add(new SearchData(className,id));

                        }

                        for(int i = 0 ; i < sectionNameArray.length() ; i++){

                            String sectionName = sectionNameArray.getJSONObject(i).getString("section_name");
                            sections.add(sectionName);
                            int id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName,id));

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(classData.size() > 0){

                    ArrayAdapter adapter1 = new ArrayAdapter(AddHomeWorkActivity.this,R.layout.spinner_row_layout,R.id.spn_text,classes);
                    spnClass.setAdapter(adapter1);

                    ArrayAdapter adapter2 = new ArrayAdapter(AddHomeWorkActivity.this,R.layout.spinner_row_layout,R.id.spn_text,sections);
                    spnSection.setAdapter(adapter2);

                    spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(i > 0){
                                section_name = classData.get(i-1).getKey();
                                section_id = classData.get(i-1).getValue();
//                                Toast.makeText(getApplicationContext(),sectionData.get(i-1).getKey()+"   "+sectionData.get(i-1).getValue(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(i > 0){

                                class_name = classData.get(i-1).getKey();
                                class_id = classData.get(i-1).getValue();
//                                Toast.makeText(getApplicationContext(),classData.get(i-1).getKey()+"   "+classData.get(i-1).getValue(),Toast.LENGTH_SHORT).show();
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

    void getAllSUbject(int id){

        subjects.clear();
        subjectData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getTeacherSubject(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){


                        JSONArray array = response.getJSONObject("data").getJSONArray("subjectsName");

                        subjects.add("select subject");

                        for(int i = 0 ; i < array.length() ; i++){

                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            int subject_id = array.getJSONObject(i).getInt("subject_id");

                            subjectData.add(new SearchData(subject_name,subject_id));
                            subjects.add(subject_name);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(subjects.size() > 0){

                    ArrayAdapter adapter = new ArrayAdapter(AddHomeWorkActivity.this,R.layout.spinner_row_layout,R.id.spn_text,subjects);
                    spnSubject.setAdapter(adapter);

                    spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(i > 0){
                                subject_id = subjectData.get(i-1).getValue();

                                Toast.makeText(getApplicationContext(),subject_id+"",Toast.LENGTH_SHORT).show();

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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.assign_date:
                datePickerDialog = new DatePickerDialog(AddHomeWorkActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mAssign_date = selectDate(dayOfMonth, month, year);
                        txtAssign.setText(mAssign_date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.submission_date:
                datePickerDialog = new DatePickerDialog(AddHomeWorkActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mSubmission_date = selectDate(dayOfMonth, month, year);
                        txtSubmission.setText(mSubmission_date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;
            case R.id.attach_file:
                Toast.makeText(getApplicationContext(),"attach file",Toast.LENGTH_SHORT).show();
                break;

        }

    }

    public String selectDate(int day, int month, int year) {

        String monthYear;
        String dayMonth;

        if (month + 1 < 10) {
            monthYear = "0" + String.valueOf(month + 1);
        } else {
            monthYear = String.valueOf(month + 1);
        }
        if (day < 10) {
            dayMonth = "0" + String.valueOf(day);
        } else {
            dayMonth = String.valueOf(day);
        }

        return dayMonth + "/" + monthYear + "/" + String.valueOf(year);

    }

}
