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
import com.infix.edu.adapter.BookIssuedAdapter;
import com.infix.edu.adapter.LeaveListAdapter;
import com.infix.edu.model.BookIssue;
import com.infix.edu.model.LeaveList;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveListActivity extends AppCompatActivity {

    private ArrayList<LeaveList> leaves = new ArrayList<>();
    private RecyclerView recyclerView;
    private LeaveListAdapter adapter;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private int id;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_list);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);

        id = sharedPreferences.getInt("id",0);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Leave List");

        //main recyclerview
        recyclerView = findViewById(R.id.leave_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllLeaves(id);
        MyConfig.getProfileImage(profile_image_url,profile,LeaveListActivity.this);

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

    void getAllLeaves(int id){

        leaves.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getLeaveList(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("leave_list");



                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("type");
                            String applay_date = array.getJSONObject(i).getString("apply_date");
                            String leave_from = array.getJSONObject(i).getString("leave_from");
                            String leave_to = array.getJSONObject(i).getString("leave_to");
                            String approve_status = array.getJSONObject(i).getString("approve_status");

                            LeaveList leaveList = new LeaveList(title,applay_date,leave_from,leave_to,approve_status);
                            leaves.add(leaveList);
                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(leaves.size() > 0){

                    adapter = new LeaveListAdapter(leaves,getApplicationContext());
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
