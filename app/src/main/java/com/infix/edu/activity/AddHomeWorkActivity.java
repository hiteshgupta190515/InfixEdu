package com.infix.edu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
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
import com.infix.edu.model.HomeWork;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.MyConfig;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

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
    private EditText etMarks;
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
    public static final MediaType FORM = MediaType.parse("multipart/form-data");


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
        etMarks = findViewById(R.id.marks);

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
                String marks = etMarks.getText().toString();

                AddHomeWork homeWork = new AddHomeWork(mAssign_date,mSubmission_date,description,class_id,section_id,subject_id,id);

                if(path != null && !TextUtils.isEmpty(marks)){
                    send_data_to_server(homeWork,marks);
                }

            }

        });

    }

    private void send_data_to_server(final AddHomeWork homeWork, final String marks) {


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                File f = new File(path.getPath());

                String content_type = getMimeType(f.getPath());
                String file_path = f.getPath();
                String file_name = file_path.substring(file_path.lastIndexOf("/") + 1);


                RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(FORM)
                        .addFormDataPart("class", String.valueOf(homeWork.getClassId()))
                        .addFormDataPart("section", String.valueOf(homeWork.getSectionId()))
                        .addFormDataPart("subject", String.valueOf(homeWork.getSubjectId()))
                        .addFormDataPart("assign_date", String.valueOf(homeWork.getAssign_date()))
                        .addFormDataPart("submission_date", String.valueOf(homeWork.getSubmission_date()))
                        .addFormDataPart("description", String.valueOf(homeWork.getDescription()))
                        .addFormDataPart("teacher_id", String.valueOf(homeWork.getTeacherId()))
                        .addFormDataPart("marks",marks)
                        .addFormDataPart("homework_file",file_name,file_body)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(MyConfig.UPLOAD_HOMEWORK)
                        .post(request_body)
                        .build();

                try {

                    okhttp3.Response response = client.newCall(request).execute();

                    if (!response.isSuccessful()) {
                        throw new IOException("Error : " + response);
                    }else{

                        String json_responce = response.body().string();

                        try {
                            JSONObject jsonObject = new JSONObject(json_responce);

                          if(jsonObject.getBoolean("success")){

                              AddHomeWorkActivity.this.runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      showSuccess();
                                  }
                              });

                          }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        t.start();
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

    public void onActivityResult(int requestCode, int resultCode, final Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

//                path = result.getData();

                File f  = new File(result.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));

                path = Uri.parse(f.getAbsolutePath());


                txt_attach_file.setText(path.getPath());


            }
        }
    }

    public String getPathFromUri(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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

        return year + "-" + monthYear + "-" + dayMonth;

    }

    public void getFile() {
//        Intent intent = new Intent();
//        intent.setType("*/*");
//        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        startActivityForResult(Intent.createChooser(intent, "Select file"), 1);


        new MaterialFilePicker()
                .withActivity(AddHomeWorkActivity.this)
                .withRequestCode(1)
                .start();

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



    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    private void showSuccess(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddHomeWorkActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(AddHomeWorkActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Homework Uploaded successfully!");

        alertBuilder.setView(mView);
        AlertDialog dialog = alertBuilder.create();

        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        mView.setMinimumWidth((int)(displayRectangle.width()));
        mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }

}
