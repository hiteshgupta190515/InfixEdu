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
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.adapter.ChildListAdapter;
import com.infix.edu.model.Child;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildListActivity extends AppCompatActivity {

    private ArrayList<Child> childs = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChildListAdapter adapter;
    private int id;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private CircleImageView profile;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_list);

        recyclerView = findViewById(R.id.childlistRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Child List");
        profile = findViewById(R.id.profile);

        SharedPreferences sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        id = sharedPreferences.getInt("id", 0);
        email = sharedPreferences.getString("email", null);
        password = sharedPreferences.getString("password",null);

        //getting all child list from server
        getAllChild(id);
        //update parents profile
        update_profile_image();

    }

    void getAllChild(int id){

        childs.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getParentChildList(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");



                        for(int i = 0 ; i < array.length() ; i++){


                            String name = array.getJSONObject(i).getString("student_name");
                            String url = array.getJSONObject(i).getString("student_photo");
                            String mClass = array.getJSONObject(i).getString("class_name");
                            int roll = array.getJSONObject(i).getInt("roll_no");
                            String section = array.getJSONObject(i).getString("section_name");
                            int id = array.getJSONObject(i).getInt("user_id");

                            Child child = new Child(name,url,mClass,section,roll,id);
                            childs.add(child);


                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(childs.size() > 0){

                    adapter = new ChildListAdapter(childs, ChildListActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

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

                        Glide.with(ChildListActivity.this)
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
                Toast.makeText(ChildListActivity.this, "server error", Toast.LENGTH_LONG).show();
            }
        });
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
