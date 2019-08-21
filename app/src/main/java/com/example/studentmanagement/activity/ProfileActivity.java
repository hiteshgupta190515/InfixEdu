package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.fragment.OthersFragment;
import com.example.studentmanagement.fragment.ParentsFragment;
import com.example.studentmanagement.fragment.PersonalFragment;
import com.example.studentmanagement.fragment.StudentFragment;
import com.example.studentmanagement.fragment.TransportFragment;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private Fragment profileFragment;
    private TextView txtPersonal,txtTransport,txtParent,txtOther;
    private TextView txtName,txtClassSection,txtRollAdm;

    private String email;
    private String password;
    private String name;
    private String height;
    private String weight;
    private String castle;
    private String nationalId;
    private String fatherName;
    private String fatherPhone;
    private String fatherOcupation;
    private String motherName;
    private String motherPhone;
    private String motherOcupation;
    private String gurdianName;
    private String gurdianPhone;
    private String gurdianOcupation;
    private String gurdianRelation;
    private String gurdianEmail;
    private String driverName;
    private String driverCarNo;
    private String driverCarModel;
    private String driverCarDeatils;
    private String section;
    private String roll;
    private String className;
    private String admission_no;
    private String religion;
    private String presentAddress;
    private String permanentAddress;
    private String bloodGroup;
    private String dateOfBirth;
    private String phone;
    private int rule;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtPersonal = findViewById(R.id.personal);
        txtParent = findViewById(R.id.parents);
        txtTransport = findViewById(R.id.transport);
        txtOther = findViewById(R.id.others);

        txtName = findViewById(R.id.student_name);
        txtClassSection = findViewById(R.id.student_class_section);
        txtRollAdm = findViewById(R.id.student_roll_adm);

        txtOther.setOnClickListener(this);
        txtParent.setOnClickListener(this);
        txtTransport.setOnClickListener(this);
        txtPersonal.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("email",null);
        password = sharedPreferences.getString("password",null);


        update(email,password);

        profileFragment = new PersonalFragment();
        replaceFragment(profileFragment);

    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_fragmengt_container,fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.personal:
                profileFragment = new PersonalFragment();
                replaceFragment(profileFragment);
                break;
            case R.id.parents:
                profileFragment = new ParentsFragment();
                replaceFragment(profileFragment);
                break;
            case R.id.transport:
                profileFragment = new TransportFragment();
                replaceFragment(profileFragment);
                break;
            case R.id.others:
                profileFragment = new OthersFragment();
                replaceFragment(profileFragment);
                break;

        }

    }

    private void update(final String email, final String password){

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConfig.getLoginUrl(email,password),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.contains("success")){

                            //Creating a shared preference
                            SharedPreferences sharedPreferences = ProfileActivity.this.getSharedPreferences("default", Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            JSONObject rootObj;
                            JSONObject secdObj = null;
                            JSONObject detailsObj;
                            JSONObject driverObj;
                            try {
                                rootObj = new JSONObject(response);
                                secdObj = rootObj.getJSONObject("data").getJSONObject("user");
                                detailsObj = rootObj.getJSONObject("data").getJSONObject("userDetails");
                                driverObj = rootObj.getJSONObject("data").getJSONObject("transport");

                                rule = secdObj.getInt("role_id");
                                id = secdObj.getInt("id");
                                fatherName = detailsObj.getString("fathers_name");
                                fatherOcupation = detailsObj.getString("fathers_occupation");
                                fatherPhone = detailsObj.getString("fathers_mobile");
                                motherName = detailsObj.getString("mothers_name");
                                motherPhone = detailsObj.getString("mothers_mobile");
                                motherOcupation = detailsObj.getString("mothers_occupation");
                                gurdianName = detailsObj.getString("guardians_name");
                                gurdianPhone = detailsObj.getString("guardians_mobile");
                                gurdianOcupation = detailsObj.getString("guardians_occupation");
                                gurdianEmail = detailsObj.getString("guardians_email");
                                gurdianRelation = detailsObj.getString("guardians_relation");
                                name = detailsObj.getString("full_name");
                                section = detailsObj.getString("section_name");
                                roll = detailsObj.getString("roll_no");
                                height = detailsObj.getString("height");
                                weight = detailsObj.getString("weight");
                                castle = detailsObj.getString("caste");
                                nationalId = detailsObj.getString("national_id_no");
                                className = detailsObj.getString("class_name");
                                admission_no = detailsObj.getString("admission_no");
                                presentAddress = detailsObj.getString("current_address");
                                permanentAddress = detailsObj.getString("permanent_address");
                                religion = rootObj.getJSONObject("data").getJSONObject("religion").getString("name");
                                bloodGroup = rootObj.getJSONObject("data").getJSONObject("blood_group").getString("name");
                                dateOfBirth = detailsObj.getString("date_of_birth");
                                phone = detailsObj.getString("mobile");

                                driverName = driverObj.getString("driver_name");
                                driverCarNo = driverObj.getString("vehicle_no");
                                driverCarModel = driverObj.getString("vehicle_model");
                                driverCarDeatils = driverObj.getString("note");

                                txtName.setText(name);
                                txtClassSection.setText("Class : "+className+"   Section : "+section);
                                txtRollAdm.setText("Roll : "+roll+"   Admission no : "+admission_no);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Adding values to editor
                            editor.putBoolean("isLoged", true);
                            editor.putString("email", email);
                            editor.putString("name", name);
                            editor.putString("height", height);
                            editor.putString("weight", weight);
                            editor.putString("castle", castle);
                            editor.putString("nationalId",nationalId);
                            editor.putString("fatherName", fatherName);
                            editor.putString("fatherPhone", fatherPhone);
                            editor.putString("fatherOccupation", fatherOcupation);
                            editor.putString("motherName", motherName);
                            editor.putString("motherOcupation", motherOcupation);
                            editor.putString("motherPhone", motherPhone);
                            editor.putString("gurdName", gurdianName);
                            editor.putString("gurdOcupation", gurdianOcupation);
                            editor.putString("gurdPhone", gurdianPhone);
                            editor.putString("gurdRelation", gurdianRelation);
                            editor.putString("gurdEmail", gurdianEmail);
                            editor.putString("driverName", driverName);
                            editor.putString("driverCarNo", driverCarNo);
                            editor.putString("drivercarModel", driverCarModel);
                            editor.putString("driverDetails", driverCarDeatils);
                            editor.putString("section", section);
                            editor.putString("roll", roll);
                            editor.putString("className", className);
                            editor.putString("admissionNo", admission_no);
                            editor.putString("permanentAd", permanentAddress);
                            editor.putString("presentAd", presentAddress);
                            editor.putString("bloodGroup", bloodGroup);
                            editor.putString("dateOfBirth", dateOfBirth);
                            editor.putString("religion", religion);
                            editor.putString("phone", phone);
                            editor.putInt("role", rule);
                            editor.putInt("id", id);

                            //Saving values to editor
                            editor.commit();


//                            Toast.makeText(ProfileActivity.this, "updated", Toast.LENGTH_LONG).show();

                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
//                            Toast.makeText(ProfileActivity.this, "update not successful", Toast.LENGTH_LONG).show();
                            Log.e("volley", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
//                        Toast.makeText(ProfileActivity.this, "please check internet connection", Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("email", email);
                params.put("password", password);

                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
