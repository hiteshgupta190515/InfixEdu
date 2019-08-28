package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.HomeAdapterHome;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private String[] names;
    private RecyclerView recycler;
    private StaggeredGridLayoutManager gridLayoutManager;
    private int role_id;
    private int id;
    private String email;
    private String password;
    private String name;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recycler = findViewById(R.id.home_recycler);
        profile = findViewById(R.id.profile);

        gridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        role_id = sharedPreferences.getInt("role", 0);
        id = sharedPreferences.getInt("id", 0);
        email = sharedPreferences.getString("email", null);
        password = sharedPreferences.getString("password",null);
        name = sharedPreferences.getString("name", null);

        getFunctionality(role_id);
        update_profile_image(email,password);

    }

    private void getFunctionality(int role) {

        ArrayList<Integer> images = new ArrayList<>();
        images.clear();

        switch (role){
            case 1:
                names = getResources().getStringArray(R.array.admins_functions_name);
                break;
            case 2:
                names = getResources().getStringArray(R.array.students_functions_name);
                break;
            case 3:
                names = getResources().getStringArray(R.array.parents_functions_name);
                break;
                case 4:
                    names = getResources().getStringArray(R.array.teachers_functions_name);
                break;

                default:
                    //names = getResources().getStringArray(R.array.teachers_functions_name);
                    break;

        }

        for(String n : names){

            images.add(imageSearch(n.toLowerCase().replace(" ","")));
        }

        HomeAdapterHome homeAdapterHome = new HomeAdapterHome(this,names,images,this);
        recycler.setAdapter(homeAdapterHome);

    }
    private int imageSearch(String text) {

        int img = 0;
        try{

            img = getResources().getIdentifier(text, "mipmap", getPackageName());

            if(img == 0){
                img = getResources().getIdentifier("inventory", "mipmap", getPackageName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
  return img;
    }



    @Override
    public void onBackPressed() {

        finish();

    }

    private void update_profile_image(final String email, final String password){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyConfig.getLoginUrl(email, password), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");
                        String image = detailsObj.getString("student_photo");

                        Glide.with(HomeActivity.this)
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
                Toast.makeText(HomeActivity.this, "Loading error", Toast.LENGTH_LONG).show();
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
