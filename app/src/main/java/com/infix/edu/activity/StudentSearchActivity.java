package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.android.material.textfield.TextInputEditText;
import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class StudentSearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private Spinner spnClass;
    private Spinner spnSection;
    private Button btnSearch;
    private ArrayList<SearchData> classData = new ArrayList<>();
    private ArrayList<SearchData> sectionData = new ArrayList<>();
    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<String> sections = new ArrayList<>();
    private int class_id;
    private int section_id;
    private String class_name;
    private String section_name;
    private TextInputEditText etName;
    private TextInputEditText etRoll;
    private String status;
    private LinearLayout linearLayout;
    private DatePickerDialog datePickerDialog;
    private Helper helper = new Helper();
    private TextView txt_date;
    private String current_date;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);

        etName = findViewById(R.id.etSearchByName);
        etRoll = findViewById(R.id.etSearchByROll);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        spnClass = findViewById(R.id.choose_class_spinner);
        spnSection = findViewById(R.id.choose_section_spinner);
        btnSearch = findViewById(R.id.btnSearch);
        linearLayout = findViewById(R.id.update_date);
        txt_date = findViewById(R.id.update_date_text);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Student Search");

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        status = getIntent().getStringExtra("status");

        if(status.equalsIgnoreCase("admin_attendance")){
            etRoll.setVisibility(View.GONE);
            etName.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);

            setDateToTextView();
        }


        getClassAndSectionName();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String roll = etRoll.getText().toString();

                Intent intent = new Intent(StudentSearchActivity.this,TeacherStudentActivity.class);
                intent.putExtra("classid",class_id);
                intent.putExtra("sectionid",section_id);
                intent.putExtra("status",status);
                intent.putExtra("name",name);
                intent.putExtra("roll",roll);
                intent.putExtra("date",current_date);
                startActivity(intent);

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateToTextView();
            }
        });


    }

    private void setDateToTextView() {

        datePickerDialog = new DatePickerDialog(StudentSearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                current_date = helper.selectDate(dayOfMonth, month, year);
                txt_date.setText(current_date);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

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

                    ArrayAdapter adapter1 = new ArrayAdapter(StudentSearchActivity.this,R.layout.spinner_row_layout,R.id.spn_text,classes);
                    spnClass.setAdapter(adapter1);

                    ArrayAdapter adapter2 = new ArrayAdapter(StudentSearchActivity.this,R.layout.spinner_row_layout,R.id.spn_text,sections);
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

}
