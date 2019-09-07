package com.infix.studentmanagement.activity;

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
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.adapter.NoticeAdapter;
import com.infix.studentmanagement.model.Notice;
import com.infix.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    private ArrayList<Notice> notices = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private int id;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private TextView txtToolbarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.noticeRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Notice Board");


        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);

        getAllNotice(id);


    }



    void getAllNotice(int id){

        notices.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getNoticeUrl(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){


                        JSONArray array = response.getJSONObject("data").getJSONArray("allNotices");



                        for(int i = 0 ; i < array.length() ; i++){


                            String title = array.getJSONObject(i).getString("notice_title");
                            String date = array.getJSONObject(i).getString("notice_date");
                            String des = array.getJSONObject(i).getString("notice_message");

                            Notice notice = new Notice(title,des,date);

                            notices.add(notice);

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(notices.size() > 0){

                    adapter = new NoticeAdapter(notices, NoticeActivity.this);
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
