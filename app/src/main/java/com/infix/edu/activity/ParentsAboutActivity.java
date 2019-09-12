package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infix.edu.adapter.PersonalAdapter;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.PersonalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentsAboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private PersonalAdapter adapter;
    private TextView txtHeader;
    private String email;
    private String password;
    private SharedPreferences sharedPreferences;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_about);

        txtHeader = findViewById(R.id.about_header);
        recyclerView = findViewById(R.id.about_details_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ParentsAboutActivity.this));


        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        email = sharedPreferences.getString("email", null);
        password = sharedPreferences.getString("password",null);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        profile = findViewById(R.id.profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("About");

        getAbouts();
        update_profile_image();

    }

    private void getAbouts(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.ABOUT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data");

                        String address = detailsObj.getString("address");
                        String phone = detailsObj.getString("phone");
                        String email = detailsObj.getString("email");
                        String school_code = detailsObj.getString("school_code");
                        String session = detailsObj.getString("session");
                        String main_description = detailsObj.getString("main_description");


                        personalData.add(new PersonalData("address",address));
                        personalData.add(new PersonalData("phone",phone));
                        personalData.add(new PersonalData("email",email));
                        personalData.add(new PersonalData("school code",school_code));
                        personalData.add(new PersonalData("session",session));

                        txtHeader.setText(main_description);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new PersonalAdapter(personalData,ParentsAboutActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ParentsAboutActivity.this, "server error,try again later", Toast.LENGTH_LONG).show();
            }
        });
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(ParentsAboutActivity.this);
        requestQueue.add(request);
    }

    private void update_profile_image(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getLoginUrl(email, password), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");
                        String image = detailsObj.getString("fathers_photo");

                        Glide.with(ParentsAboutActivity.this)
                                .load(MyConfig.ROOT_URL+image)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .fitCenter()
                                .into(profile);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ParentsAboutActivity.this, "server error", Toast.LENGTH_LONG).show();
            }
        });
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


}
