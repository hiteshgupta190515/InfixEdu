package com.infix.edu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.infix.edu.adapter.StudentListAdapter;
import com.infix.edu.model.AddHomeWork;
import com.infix.edu.model.SearchData;
import com.infix.edu.model.Tstudent;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ContentAddActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String strRadio;
    private boolean isChecked = false;
    private ArrayList<SearchData> classArray = new ArrayList<>();
    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<SearchData> sectionArray = new ArrayList<>();
    private ArrayList<String> sections = new ArrayList<>();
    private int class_id;
    private int section_id;
    private String class_name;
    private String section_name;
    private String update_date;
    private LinearLayout update_date_linear;
    private RelativeLayout attach_file;
    private TextView txt_attach_file;
    private MediaType FORM = MediaType.parse("multipart/form-data");
    private static final int STORAGE_PERMISSION_CODE = 123;
    private boolean isPermissionGranted = false;
    private Uri path;
    private int year, month, day;
    private TextView txtUpdatedate;
    private DatePickerDialog datePickerDialog;
    private Helper helper = new Helper();
    private Spinner spn_content_type;
    private String[] content_type;
    private String contentStr;
    private EditText et_title;
    private EditText et_reason;
    private Button btn_save_content;
    private int allClasses;
    private SharedPreferences sharedPreferences;
    private int id;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add);

        radioGroup = findViewById(R.id.radio_group);
        update_date_linear = findViewById(R.id.update_date);
        update_date_linear.setOnClickListener(this);
        attach_file = findViewById(R.id.attach_file);
        attach_file.setOnClickListener(this);
        txtUpdatedate = findViewById(R.id.update_date_text);
        txt_attach_file = findViewById(R.id.txt_attach_file);
        spn_content_type = findViewById(R.id.choose_content_type_spinner);
        et_title = findViewById(R.id.content_title);
        et_reason = findViewById(R.id.reason_textbox);
        btn_save_content = findViewById(R.id.btn_save_content);
        btn_save_content.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        classArray = helper.getClassData(this);
        sectionArray = helper.getSectionData(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Upload Content");

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image", null);
        id = sharedPreferences.getInt("id", 0);
        MyConfig.getProfileImage(profile_image_url, profile, ContentAddActivity.this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.all_admin:

                        radioButton = findViewById(R.id.all_admin);
                        strRadio  = "admin";
                        break;

                    case R.id.student:

                        radioButton = findViewById(R.id.student);
                        strRadio  = "student";
                        showSelectOption();
                        break;

                }
            }

        });

        content_type = getResources().getStringArray(R.array.content_type_name);
        ArrayAdapter adapter = new ArrayAdapter(ContentAddActivity.this, R.layout.spinner_row_layout, R.id.spn_text, content_type);
        spn_content_type.setAdapter(adapter);

        spn_content_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 0) {
                    contentStr = content_type[i].substring(0,2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    private void send_data_to_server(final String title, final String description) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                File f = new File(path.getPath());

                String content_type = helper.getMimeType(f.getPath());
                String file_path = f.getPath();
                final String file_name = file_path.substring(file_path.lastIndexOf("/") + 1);


                RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                RequestBody request_body = new MultipartBody.Builder()
                        .setType(FORM)
                        .addFormDataPart("content_title",title)
                        .addFormDataPart("content_type", contentStr)
                        .addFormDataPart("available_for", strRadio)
                        .addFormDataPart("all_classes", String.valueOf(allClasses))
                        .addFormDataPart("class", String.valueOf(class_id))
                        .addFormDataPart("section", String.valueOf(section_id))
                        .addFormDataPart("upload_date", update_date)
                        .addFormDataPart("description",description)
                        .addFormDataPart("created_by", String.valueOf(id))
                        .addFormDataPart("attach_file",file_name,file_body)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(MyConfig.UPLOAD_CONTENT)
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

                                ContentAddActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showSuccess();

                                        if(strRadio.equals("admin")){
                                            helper.sentNotificationForAll(1,ContentAddActivity.this);
                                            Toast.makeText(getApplicationContext(),"admin",Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(allClasses == 1){
                                                helper.sentNotificationForAll(2,ContentAddActivity.this);
                                                Toast.makeText(getApplicationContext(),"studnet",Toast.LENGTH_SHORT).show();
                                            }else{
                                                getAllStudentByClass(class_id,section_id);
                                                Toast.makeText(getApplicationContext(),"class section",Toast.LENGTH_SHORT).show();
                                            }
                                        }
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

    public void onActivityResult(int requestCode, int resultCode, final Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//                path = result.getData();
//                txt_attach_file.setText(helper.getPathFromUri(path,this));
                File f  = new File(result.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));

                path = Uri.parse(f.getAbsolutePath());


                txt_attach_file.setText(path.getPath());

            }
        }
    }

    private void showSelectOption(){


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ContentAddActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(ContentAddActivity.this).inflate(R.layout.student_content_layout, null);

        final RadioButton radioButton = mView.findViewById(R.id.available);
        final Spinner spnClass = mView.findViewById(R.id.choose_classes_spinner);
        final Spinner spnSection = mView.findViewById(R.id.choose_section_spinner);

        for(SearchData s: classArray){
            classes.add(s.getKey());
        }
        for(SearchData s: sectionArray){
            sections.add(s.getKey());
        }


        ArrayAdapter adapter1 = new ArrayAdapter(ContentAddActivity.this, R.layout.spinner_row_layout, R.id.spn_text, classes);
        spnClass.setAdapter(adapter1);

        ArrayAdapter adapter2 = new ArrayAdapter(ContentAddActivity.this, R.layout.spinner_row_layout, R.id.spn_text, sections);
        spnSection.setAdapter(adapter2);

       radioButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!isChecked) {
                   isChecked = true;

                   spnClass.setVisibility(View.GONE);
                   spnSection.setVisibility(View.GONE);

                   class_id = -1;
                   section_id = -1;
                   allClasses = 1;

               }else{

                   isChecked = false;
                   radioButton.setChecked(isChecked);

                   spnClass.setVisibility(View.VISIBLE);
                   spnSection.setVisibility(View.VISIBLE);
                   allClasses = 0;

               }
           }
       });

        spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 0) {
                    section_name = sectionArray.get(i).getKey();
                    section_id = sectionArray.get(i).getValue();
                                Toast.makeText(getApplicationContext(),section_name+"   "+section_id,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 0) {

                    class_name = classArray.get(i).getKey();
                    class_id = classArray.get(i).getValue();
                                Toast.makeText(getApplicationContext(),class_name+"   "+class_id,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.update_date:

                datePickerDialog = new DatePickerDialog(ContentAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        update_date = helper.selectDate(dayOfMonth, month, year);
                        txtUpdatedate.setText(update_date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                break;

            case R.id.attach_file:

                helper.getFile(ContentAddActivity.this);

                break;

            case R.id.btn_save_content:

                requestStoragePermission();

                if(isPermissionGranted) {

                    String title = et_title.getText().toString();
                    String reason = et_reason.getText().toString();


                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(reason) && !TextUtils.isEmpty(strRadio) && !TextUtils.isEmpty(update_date) &&
                            !TextUtils.isEmpty(path.toString())) {

                        send_data_to_server(title, reason);

                    }

                }

                break;

        }

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

    private void showSuccess(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ContentAddActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(ContentAddActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Content Uploaded successfully!");

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

    public void getAllStudentByClass(int classId,int sectionId){

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentByClassAndSection(classId,sectionId), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.getBoolean("success")){


                                JSONArray jsonArray = response.getJSONObject("data").getJSONArray("students");

                                for(int i = 0 ; i < jsonArray.length() ; i++){


                                    String token = jsonArray.getJSONObject(i).getString("notificationToken");

                                    helper.sentNotification("Content upload","A new content uploaded please check",token,ContentAddActivity.this);
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }

}
