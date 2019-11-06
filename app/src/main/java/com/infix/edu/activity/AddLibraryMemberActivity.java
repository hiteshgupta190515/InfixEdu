package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;

import java.util.ArrayList;

public class AddLibraryMemberActivity extends AppCompatActivity {

    private Spinner spnMemberCategory,spnClass,spnSection,spnStuff;
    private ArrayList<SearchData> categories,classes,sections,stuffs;
    private ArrayList<String> categorieStr,classesStr,sectionsStr,stuffStr;
    private int category,mClass,mSection,mStuff;
    private SharedPreferences sharedPreferences;
    private int id;
    private Helper helper = new Helper();
    private TextView txtToolbarText;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_library_member);
        spnMemberCategory = findViewById(R.id.choose_member_type_spinner);
        spnClass = findViewById(R.id.choose_class_spinner);
        spnSection = findViewById(R.id.choose_section_spinner);
        spnStuff = findViewById(R.id.choose_student_spinner);
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        categories = new ArrayList<>();
        classes = new ArrayList<>();
        sections = new ArrayList<>();
        stuffs = new ArrayList<>();
        classesStr = new ArrayList<>();
        sectionsStr = new ArrayList<>();
        categorieStr = new ArrayList<>();
        stuffStr = new ArrayList<>();
        categorieStr.add("select category");

        id = sharedPreferences.getInt("id",0);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Member");

        categories = helper.getAllMemberType(this);
        classes = helper.getClassData(this);
        sections = helper.getSectionData(this);
        getMemberType();

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

    void getMemberType(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < categories.size() ; i++){
                    categorieStr.add(categories.get(i).getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddLibraryMemberActivity.this, R.layout.spinner_row_layout, R.id.spn_text, categorieStr);
                spnMemberCategory.setAdapter(adapter);

                spnMemberCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i > 0) {
                            category = categories.get(i - 1).getValue();
                            showOption(category);
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        },2000);

    }
    void getClasses(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < classes.size() ; i++){
                    classesStr.add(classes.get(i).getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddLibraryMemberActivity.this, R.layout.spinner_row_layout, R.id.spn_text, classesStr);
                spnClass.setAdapter(adapter);

                spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i >= 0) {
                            mClass = classes.get(i).getValue();
                            Toast.makeText(getApplicationContext(),mClass+"",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        },2000);
    }
    void getSections(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < sections.size() ; i++){
                    sectionsStr.add(sections.get(i).getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddLibraryMemberActivity.this, R.layout.spinner_row_layout, R.id.spn_text, sectionsStr);
                spnSection.setAdapter(adapter);

                spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i >= 0) {
                            mSection = sections.get(i).getValue();
                            Toast.makeText(getApplicationContext(),mSection+"",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        },2000);
    }
    void getStuff(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < stuffs.size() ; i++){
                    stuffStr.add(stuffs.get(i).getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddLibraryMemberActivity.this, R.layout.spinner_row_layout, R.id.spn_text, stuffStr);
                spnStuff.setAdapter(adapter);

                spnStuff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i >= 0) {
                            mStuff = stuffs.get(i).getValue();
                            Toast.makeText(getApplicationContext(),mStuff+"",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        },2000);
    }

    void showOption(int cat){
        stuffs.clear();
        stuffStr.clear();
        stuffs = helper.getAllStaff(cat,AddLibraryMemberActivity.this);
        if(cat == 2){
            getClasses();
            getSections();
            spnSection.setVisibility(View.VISIBLE);
            spnClass.setVisibility(View.VISIBLE);
            spnStuff.setVisibility(View.GONE);
        }else{
            spnSection.setVisibility(View.GONE);
            spnClass.setVisibility(View.GONE);
            spnStuff.setVisibility(View.VISIBLE);
            getStuff();
        }

    }



}
