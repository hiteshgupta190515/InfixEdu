package com.infix.edu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddDormitoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    private ArrayList<SearchData> roomTypes = new ArrayList<>();
    private ArrayList<String> roomTypeData = new ArrayList<>();
    private Spinner spn_room_type;
    private String type_id;
    private String[] intake;
    private Button btn_save;
    private EditText et_dormitory_name,et_address,et_description,et_intake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dormitory);

        spn_room_type = findViewById(R.id.choose_dormitory_type_spinner);
        btn_save = findViewById(R.id.btn_save);
        et_address = findViewById(R.id.et_address);
        et_description = findViewById(R.id.et_description);
        et_dormitory_name = findViewById(R.id.et_dormitory_name);
        et_intake = findViewById(R.id.et_intake);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        intake = getResources().getStringArray(R.array.intake_option);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Dormitory");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,AddDormitoryActivity.this);

        showSpinnerData();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_dormitory_name.getText().toString();
                String address = et_address.getText().toString();
                String description = et_description.getText().toString();
                String intake = et_intake.getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(intake)){

                    send_dormitory_data(name,intake,String.valueOf(type_id),description,address);

                }

            }
        });

    }

    private void showSpinnerData(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ArrayAdapter adapter1 = new ArrayAdapter(AddDormitoryActivity.this, R.layout.spinner_row_layout, R.id.spn_text, intake);
                spn_room_type.setAdapter(adapter1);

                spn_room_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if(adapterView.getSelectedItem().toString().equalsIgnoreCase("Boys"))
                            type_id = "B";
                        else
                            type_id = "G";

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });



            }
        }, 1000);
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

    void send_dormitory_data(final String dormitory_name, final String intake, final String type, final String description, final String address){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Toast.makeText(getApplicationContext(),dormitory_name+" "+intake+" "+type+" "+description+" "+address,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyConfig.addDormitory(dormitory_name,type,intake,address,description), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("success"))

                        showSuccess();

                    else
                        Toast.makeText(AddDormitoryActivity.this,"data does not save",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddDormitoryActivity.this,"server does not response",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    private void showSuccess(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddDormitoryActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(AddDormitoryActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Dormitory add successfully!");

        alertBuilder.setView(mView);
        AlertDialog dialog = alertBuilder.create();

        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        mView.setMinimumWidth((int)(displayRectangle.width()));
        mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

}
