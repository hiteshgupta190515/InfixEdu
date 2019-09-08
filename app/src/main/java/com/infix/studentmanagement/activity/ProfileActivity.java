package com.infix.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.fragment.OthersFragment;
import com.infix.studentmanagement.fragment.ParentsFragment;
import com.infix.studentmanagement.fragment.PersonalFragment;
import com.infix.studentmanagement.fragment.TransportFragment;
import com.infix.studentmanagement.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private Fragment profileFragment;
    private TextView txtPersonal,txtTransport,txtParent,txtOther;
    private TextView txtName,txtClassSection,txtRollAdm;
    private String stName,stClass,stSection,stRoll,stAdm,image;
    private CircleImageView studentPhoto;

    private String email;
    private String password;

    private Toolbar toolbar;
    private TextView txtToolbarText;

    private String url;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtPersonal = findViewById(R.id.personal);
        txtParent = findViewById(R.id.parents);
        txtTransport = findViewById(R.id.transport);
        txtOther = findViewById(R.id.others);

        studentPhoto = findViewById(R.id.student_poster);

        txtName = findViewById(R.id.student_name);
        txtClassSection = findViewById(R.id.student_class_section);
        txtRollAdm = findViewById(R.id.student_roll_adm);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Student Info");

        txtOther.setOnClickListener(this);
        txtParent.setOnClickListener(this);
        txtTransport.setOnClickListener(this);
        txtPersonal.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("email",null);
        password = sharedPreferences.getString("password",null);

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
            url = MyConfig.getChildren(id);
        }else{
            id = sharedPreferences.getInt("id", 0);
            url = MyConfig.getLoginUrl(email, password);
        }


        update(url);

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


    private void update(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");

                                stName = detailsObj.getString("full_name");
                                stSection = detailsObj.getString("section_name");
                                stRoll = detailsObj.getString("roll_no");
                                stClass = detailsObj.getString("class_name");
                                stAdm = detailsObj.getString("admission_no");
                                image = detailsObj.getString("student_photo");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                txtName.setText(stName);
                txtClassSection.setText("Class : "+stClass+" | Section :"+stSection);
                txtRollAdm.setText("Roll : "+stRoll+" | Adm :"+stAdm);

                try {
                    Glide.with(ProfileActivity.this)
                            .load(MyConfig.ROOT_URL+image)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .fitCenter()
                            .into(studentPhoto);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "Loading error", Toast.LENGTH_LONG).show();
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

}
