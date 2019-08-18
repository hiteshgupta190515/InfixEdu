package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.MainActivity;
import com.example.studentmanagement.R;
import com.example.studentmanagement.myconfig.MyConfig;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button login;
    private String name;
    private String fatherName;
    private String motherName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                login(etEmail.getText().toString(),etPassword.getText().toString());

                //Toast.makeText(LoginActivity.this,etEmail.getText()+" "+etPassword.getText(),Toast.LENGTH_LONG).show();

            }
        });


    }


    private void login(final String email, final String password){

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConfig.getLoginUrl(email,password),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //If we are getting success from server
                        if(response.contains("success")){

                            //Creating a shared preference
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("default", Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();


                            JSONObject rootObj;
                            JSONObject secdObj = null;
                            JSONObject detailsObj;
                            try {
                                rootObj = new JSONObject(response);
                                secdObj = rootObj.getJSONObject("data").getJSONObject("user");
                                detailsObj = rootObj.getJSONObject("data").getJSONObject("userDetails");

                                rule = secdObj.getInt("role_id");
                                fatherName = detailsObj.getString("fathers_name");
                                motherName = detailsObj.getString("mothers_name");
                                name = detailsObj.getString("full_name");
                                section = detailsObj.getString("section_name");
                                roll = detailsObj.getString("roll_no");
                                className = detailsObj.getString("class_name");
                                admission_no = detailsObj.getString("admission_no");
                                presentAddress = detailsObj.getString("current_address");
                                permanentAddress = detailsObj.getString("permanent_address");
                                religion = rootObj.getJSONObject("data").getJSONObject("religion").getString("name");
                                bloodGroup = rootObj.getJSONObject("data").getJSONObject("blood_group").getString("name");
                                dateOfBirth = detailsObj.getString("date_of_birth");
                                phone = detailsObj.getString("mobile");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Adding values to editor
                            editor.putBoolean("isLoged", true);
                            editor.putString("email", email);
                            editor.putString("name", name);
                            editor.putString("fatherName", fatherName);
                            editor.putString("motherName", motherName);
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

                            //Saving values to editor
                            editor.commit();


                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();



                            //Starting profile activity
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "Invalid username/password", Toast.LENGTH_LONG).show();
                            Log.e("volley", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(LoginActivity.this, "Invalid username/password", Toast.LENGTH_LONG).show();

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
