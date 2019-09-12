package com.infix.edu.activity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.myconfig.MyConfig;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button login;
    private int rule;
    private int id;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);

        sharedPreferences = LoginActivity.this.getSharedPreferences("default", Context.MODE_PRIVATE);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                login1(etEmail.getText().toString(),etPassword.getText().toString());

                //Toast.makeText(LoginActivity.this,etEmail.getText()+" "+etPassword.getText(),Toast.LENGTH_LONG).show();

            }
        });


    }

    private void login1(final String email, final String password){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyConfig.getLoginUrl(email, password), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                try {
                    if(response.getBoolean("success")){

                        JSONObject user;

                        user = response.getJSONObject("data").getJSONObject("user");

                         id = user.getInt("id");
                         rule = user.getInt("role_id");

                        editor.putBoolean("isLoged", true);
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putInt("id", id);
                        editor.putInt("role", rule);

                        editor.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Starting profile activity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        requestQueue.add(request);
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
                            JSONObject secdObj;
                            JSONObject detailsObj;
                            JSONObject driverObj;
                            try {
                                rootObj = new JSONObject(response);
                                secdObj = rootObj.getJSONObject("data").getJSONObject("user");
                                detailsObj = rootObj.getJSONObject("data").getJSONObject("userDetails");
                                driverObj = rootObj.getJSONObject("data").getJSONObject("transport");

                                int rule1 = secdObj.getInt("role_id");
                                int id1 = secdObj.getInt("id");
//                                fatherName = detailsObj.getString("fathers_name");
//                                fatherOcupation = detailsObj.getString("fathers_occupation");
//                                fatherPhone = detailsObj.getString("fathers_mobile");
//                                motherName = detailsObj.getString("mothers_name");
//                                motherPhone = detailsObj.getString("mothers_mobile");
//                                motherOcupation = detailsObj.getString("mothers_occupation");
//                                gurdianName = detailsObj.getString("guardians_name");
//                                gurdianPhone = detailsObj.getString("guardians_mobile");
//                                gurdianOcupation = detailsObj.getString("guardians_occupation");
//                                gurdianEmail = detailsObj.getString("guardians_email");
//                                gurdianRelation = detailsObj.getString("guardians_relation");
//                                name = detailsObj.getString("full_name");
//                                height = detailsObj.getString("height");
//                                weight = detailsObj.getString("weight");
//                                castle = detailsObj.getString("caste");
//                                nationalId = detailsObj.getString("national_id_no");
//                                section = detailsObj.getString("section_name");
//                                roll = detailsObj.getString("roll_no");
//                                className = detailsObj.getString("class_name");
//                                admission_no = detailsObj.getString("admission_no");
//                                presentAddress = detailsObj.getString("current_address");
//                                permanentAddress = detailsObj.getString("permanent_address");
//                                religion = rootObj.getJSONObject("data").getJSONObject("religion").getString("name");
//                                bloodGroup = rootObj.getJSONObject("data").getJSONObject("blood_group").getString("name");
//                                dateOfBirth = detailsObj.getString("date_of_birth");
//                                phone = detailsObj.getString("mobile");
//
//                                driverName = driverObj.getString("driver_name");
//                                driverCarNo = driverObj.getString("vehicle_no");
//                                driverCarModel = driverObj.getString("vehicle_model");
//                                driverCarDeatils = driverObj.getString("note");

                                Toast.makeText(LoginActivity.this, rule1+"  "+id1, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Adding values to editor
//                            editor.putBoolean("isLoged", true);
//                            editor.putString("email", email);
//                            editor.putString("password", password);
//                            editor.putString("name", name);
//                            editor.putString("height", height);
//                            editor.putString("weight", weight);
//                            editor.putString("castle", castle);
//                            editor.putString("nationalId",nationalId);
//                            editor.putString("fatherName", fatherName);
//                            editor.putString("fatherPhone", fatherPhone);
//                            editor.putString("fatherOccupation", fatherOcupation);
//                            editor.putString("motherName", motherName);
//                            editor.putString("motherOcupation", motherOcupation);
//                            editor.putString("motherPhone", motherPhone);
//                            editor.putString("gurdName", gurdianName);
//                            editor.putString("gurdOcupation", gurdianOcupation);
//                            editor.putString("gurdPhone", gurdianPhone);
//                            editor.putString("gurdRelation", gurdianRelation);
//                            editor.putString("gurdEmail", gurdianEmail);
//                            editor.putString("driverName", driverName);
//                            editor.putString("driverCarNo", driverCarNo);
//                            editor.putString("drivercarModel", driverCarModel);
//                            editor.putString("driverDetails", driverCarDeatils);
//                            editor.putString("section", section);
//                            editor.putString("roll", roll);
//                            editor.putString("className", className);
//                            editor.putString("admissionNo", admission_no);
//                            editor.putString("permanentAd", permanentAddress);
//                            editor.putString("presentAd", presentAddress);
//                            editor.putString("bloodGroup", bloodGroup);
//                            editor.putString("dateOfBirth", dateOfBirth);
//                            editor.putString("religion", religion);
//                            editor.putString("phone", phone);
//                            editor.putInt("id", id);
//                            editor.putInt("role", rule);

                            //Saving values to editor
                            //editor.commit();
//
//                                if(rule != 0 && id != 0){
//                                    //Starting profile activity
//                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(LoginActivity.this, "responce is not success", Toast.LENGTH_LONG).show();
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
