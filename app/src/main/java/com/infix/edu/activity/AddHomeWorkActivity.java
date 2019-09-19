package com.infix.edu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import com.infix.edu.model.AddHomeWork;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.MyConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private TextView txtBrowse,txt_attach_file;
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
    private String mAssign_date, mSubmission_date;
    private TextView txtAssign, txtSubmission;

    private Uri path;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private boolean isPermissionGranted = false;
    String filePath;
    File file;


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
        txt_attach_file = findViewById(R.id.txt_attach_file);

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
        profile_image_url = sharedPreferences.getString("profile_image", null);
        id = sharedPreferences.getInt("id", 0);
        MyConfig.getProfileImage(profile_image_url, profile, AddHomeWorkActivity.this);


        getClassAndSectionName();
        getAllSUbject(id);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = etDescription.getText().toString();

                AddHomeWork homeWork = new AddHomeWork(mAssign_date,mSubmission_date,description,class_id,section_id,subject_id,id);


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

    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                path = result.getData();
                file = new File(path.getPath());

                if(file != null)
                    txt_attach_file.setText(file.toString());

                //Toast.makeText(getApplicationContext(), file + "", Toast.LENGTH_SHORT).show();

            }
        }
    }

    void getClassAndSectionName() {

        classData.clear();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getClassAndSection(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONObject main = response.getJSONObject("data");
                        JSONArray classNameArray = main.getJSONArray("classes");
                        JSONArray sectionNameArray = main.getJSONArray("sections");

                        classes.add("select classes");
                        sections.add("select section");

                        for (int i = 0; i < classNameArray.length(); i++) {

                            String className = classNameArray.getJSONObject(i).getString("class_name");
                            classes.add(className);
                            class_id = classNameArray.getJSONObject(i).getInt("id");
                            classData.add(new SearchData(className, class_id));

                        }

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("section_name");
                            sections.add(sectionName);
                            section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (classData.size() > 0) {

                    ArrayAdapter adapter1 = new ArrayAdapter(AddHomeWorkActivity.this, R.layout.spinner_row_layout, R.id.spn_text, classes);
                    spnClass.setAdapter(adapter1);

                    ArrayAdapter adapter2 = new ArrayAdapter(AddHomeWorkActivity.this, R.layout.spinner_row_layout, R.id.spn_text, sections);
                    spnSection.setAdapter(adapter2);

                    spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i > 0) {
                                section_name = classData.get(i - 1).getKey();
                                section_id = classData.get(i - 1).getValue();
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

                            if (i > 0) {

                                class_name = classData.get(i - 1).getKey();
                                class_id = classData.get(i - 1).getValue();
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
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }

    void getAllSUbject(int id) {

        subjects.clear();
        subjectData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getTeacherSubject(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {


                        JSONArray array = response.getJSONObject("data").getJSONArray("subjectsName");

                        subjects.add("select subject");

                        for (int i = 0; i < array.length(); i++) {

                            String subject_name = array.getJSONObject(i).getString("subject_name");
                            subject_id = array.getJSONObject(i).getInt("subject_id");

                            subjectData.add(new SearchData(subject_name, subject_id));
                            subjects.add(subject_name);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (subjects.size() > 0) {

                    ArrayAdapter adapter = new ArrayAdapter(AddHomeWorkActivity.this, R.layout.spinner_row_layout, R.id.spn_text, subjects);
                    spnSubject.setAdapter(adapter);

                    spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i > 0) {
                                subject_id = subjectData.get(i - 1).getValue();

                                Toast.makeText(getApplicationContext(), subject_id + "", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

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

                requestStoragePermission();

                if (isPermissionGranted) {
                    getFile();
                }
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

    public void getFile() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                //Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

//            .appendQueryParameter("class", String.valueOf(homeWork.getClassId()))
//            .appendQueryParameter("section", String.valueOf(homeWork.getSectionId()))
//            .appendQueryParameter("subject", String.valueOf(homeWork.getSubjectId()))
//            .appendQueryParameter("assign_date", String.valueOf(homeWork.getAssign_date()))
//            .appendQueryParameter("submission_date", String.valueOf(homeWork.getSubmission_date()))
//            .appendQueryParameter("description", String.valueOf(homeWork.getDescription()))
//            .appendQueryParameter("homework_file", String.valueOf(path))
//            .appendQueryParameter("teacher_id", String.valueOf(homeWork.getTeacherId()));

//    public static void executeMultipartPost(String url, String imgPath, String field1, String field2){
//        try {
//            HttpClient client = new DefaultHttpClient();
//            HttpPost poster = new HttpPost(url);
//
//            File image = new File(imgPath);  //get the actual file from the device
//            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            entity.addPart("field1", new StringBody(field1));
//            entity.addPart("field2", new StringBody(field2));
//            entity.addPart("image", new FileBody(image));
//            poster.setEntity(entity);
//
//            client.execute(poster, new ResponseHandler<Object>() {
//                public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
//                    HttpEntity respEntity = response.getEntity();
//                    String responseString = EntityUtils.toString(respEntity);
//                    // do something with the response string
//                    return null;
//                }
//            });
//        } catch (Exception e){
//            //do something with the error
//        }
//    }

}