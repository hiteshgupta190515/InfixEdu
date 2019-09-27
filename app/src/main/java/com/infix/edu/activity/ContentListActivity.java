package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.adapter.ContentListAdapter;
import com.infix.edu.adapter.DormitoryAdapter;
import com.infix.edu.model.Content;
import com.infix.edu.model.Dormitory;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContentListActivity extends AppCompatActivity {

    private ArrayList<Content> contents = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContentListAdapter adapter;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private int id;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Content list");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);

        //main recyclerview
        recyclerView = findViewById(R.id.content_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllContents();

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


    void getAllContents(){

        contents.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getAllContent(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("content_list");


                        for(int i = 0 ; i < array.length() ; i++){

                            int id = array.getJSONObject(i).getInt("id");
                            String content_title = array.getJSONObject(i).getString("content_title");
                            String content_type = array.getJSONObject(i).getString("content_type");
                            String upload_date = array.getJSONObject(i).getString("upload_date");
                            String upload_file = array.getJSONObject(i).getString("upload_file");
                            String mClass = array.getJSONObject(i).getString("class");

                            Content content = new Content(content_title,upload_file,content_type,upload_date,mClass,id);
                            contents.add(content);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(contents.size() > 0){

                    adapter = new ContentListAdapter(contents,ContentListActivity.this);
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

}
