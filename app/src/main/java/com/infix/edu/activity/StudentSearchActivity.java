package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private String class_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        spnClass = findViewById(R.id.choose_class_spinner);
        spnSection = findViewById(R.id.choose_section_spinner);
        btnSearch = findViewById(R.id.btnSearch);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Student Search");


        getClassAndSectionName();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentSearchActivity.this,TeacherStudentActivity.class);
                intent.putExtra("classid",class_id);
                startActivity(intent);

            }
        });


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

                        classes.add("select class");
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
                                Toast.makeText(getApplicationContext(),sectionData.get(i-1).getKey()+"   "+sectionData.get(i-1).getValue(),Toast.LENGTH_SHORT).show();
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
