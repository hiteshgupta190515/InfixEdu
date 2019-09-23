package com.infix.edu.activity;

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
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.infix.edu.model.ApplyLeave;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.MyConfig;

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

public class ApplyLeaveActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private LinearLayout applyDay,fromDate,toDate,attach_file;
    private TextView txtApplyDay,txtFromDate,txtToDate,txt_attach_file;
    private EditText reason;
    private Button btnApply;
    private Spinner spnType;
    private ArrayList<SearchData> typeData = new ArrayList<>();
    private ArrayList<String> types = new ArrayList<>();
    private int type_id;
    private String mApplyDate;
    private String mFromDate;
    private String mToDate;
    private int year, month, day;
    private DatePickerDialog datePickerDialog;
    private Uri path;
    private int id;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private boolean isPermissionGranted = false;
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    private String file_path;
    private String file_name;
    private File f;
    RequestBody request_body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Apply Leave");

        spnType = findViewById(R.id.choose_type_spinner);
        getLeaveType();

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image", null);
        id = sharedPreferences.getInt("id", 0);
        MyConfig.getProfileImage(profile_image_url, profile, ApplyLeaveActivity.this);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        applyDay = findViewById(R.id.apply_date);
        fromDate = findViewById(R.id.from_apply_date);
        toDate = findViewById(R.id.to_apply_date);
        attach_file = findViewById(R.id.attach_file);

        applyDay.setOnClickListener(this);
        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        attach_file.setOnClickListener(this);

        txtApplyDay = findViewById(R.id.txt_apply_date);
        txtFromDate = findViewById(R.id.txt_from_date);
        txtToDate = findViewById(R.id.txt_to_date);
        reason = findViewById(R.id.reason_textbox);
        btnApply = findViewById(R.id.btnApplyLeave);
        txt_attach_file = findViewById(R.id.txt_attach_file);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rea = reason.getText().toString().trim();


                if(!TextUtils.isEmpty(mApplyDate) && !TextUtils.isEmpty(mFromDate)
                && !TextUtils.isEmpty(mToDate)&& !TextUtils.isEmpty(rea)){

                    ApplyLeave leave = new ApplyLeave(mApplyDate,type_id,mFromDate,mToDate,id,rea);
                    send_data_to_server(leave);

                }

            }
        });


    }

    private void send_data_to_server(final ApplyLeave applyLeave){

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                if(path == null){

                     request_body = new MultipartBody.Builder()
                            .setType(FORM)
                            .addFormDataPart("apply_date", applyLeave.getApply_date())
                            .addFormDataPart("leave_type", String.valueOf(applyLeave.getLeave_type()))
                            .addFormDataPart("leave_from", applyLeave.getLeave_from())
                            .addFormDataPart("leave_to", applyLeave.getLeave_to())
                            .addFormDataPart("teacher_id", String.valueOf(applyLeave.getTeacher_id()))
                            .addFormDataPart("reason", applyLeave.getReason())
                            .build();

                }else {
                    f = new File(getPathFromUri(path,ApplyLeaveActivity.this));

                    String content_type = getMimeType(f.getPath());
                    file_path = f.getPath();
                    file_name = file_path.substring(file_path.lastIndexOf("/") + 1);

//                if(file_name.matches(".*\\d.*")){
//                    Log.d("find","found  "+content_type);
//                }else{
//                    Log.d("find","not found"+content_type);
//                }

                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

                     request_body = new MultipartBody.Builder()
                            .setType(FORM)
                            .addFormDataPart("apply_date", applyLeave.getApply_date())
                            .addFormDataPart("leave_type", String.valueOf(applyLeave.getLeave_type()))
                            .addFormDataPart("leave_from", applyLeave.getLeave_from())
                            .addFormDataPart("leave_to", applyLeave.getLeave_to())
                            .addFormDataPart("teacher_id", String.valueOf(applyLeave.getTeacher_id()))
                            .addFormDataPart("reason", applyLeave.getReason())
                            .addFormDataPart("attach_file",file_name,file_body)
                            .build();
                }

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(MyConfig.LEAVE_APPLY)
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

                                ApplyLeaveActivity.this.runOnUiThread(new Runnable() {
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

    public void onActivityResult(int requestCode, int resultCode, final Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                path = result.getData();

                txt_attach_file.setText(getPathFromUri(path,this));


//                Toast.makeText(getApplicationContext(), path.toString(), Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void getLeaveType() {

        types.clear();
        typeData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.LEAVE_TYPE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {


                        JSONArray array = response.getJSONArray("data");

                        types.add("select type");

                        for (int i = 0; i < array.length(); i++) {

                            String type_name = array.getJSONObject(i).getString("type");
                            type_id = array.getJSONObject(i).getInt("id");

                            typeData.add(new SearchData(type_name, type_id));
                            types.add(type_name);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (types.size() > 0) {

                    ArrayAdapter adapter = new ArrayAdapter(ApplyLeaveActivity.this, R.layout.spinner_row_layout, R.id.spn_text, types);
                    spnType.setAdapter(adapter);

                    spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i > 0) {
                                type_id = typeData.get(i - 1).getValue();

                                //Toast.makeText(getApplicationContext(), type_id + "", Toast.LENGTH_SHORT).show();

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

        switch (view.getId()){

            case R.id.apply_date:
                datePickerDialog = new DatePickerDialog(ApplyLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mApplyDate = MyConfig.selectDate(dayOfMonth, month, year);
                        txtApplyDay.setText(mApplyDate);

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.from_apply_date:

                datePickerDialog = new DatePickerDialog(ApplyLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mFromDate = MyConfig.selectDate(dayOfMonth, month, year);
                        txtFromDate.setText(mFromDate);

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.to_apply_date:

                datePickerDialog = new DatePickerDialog(ApplyLeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mToDate = MyConfig.selectDate(dayOfMonth, month, year);
                        txtToDate.setText(mToDate);

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

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    private void showSuccess(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ApplyLeaveActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(ApplyLeaveActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Leave data Uploaded successfully!");

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
