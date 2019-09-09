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
import com.infix.studentmanagement.adapter.TransportAdapetr;
import com.infix.studentmanagement.model.Transport;
import com.infix.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransportActivity extends AppCompatActivity {

    private ArrayList<Transport> transports = new ArrayList<>();
    private RecyclerView recyclerView;
    private TransportAdapetr adapter;

    private Toolbar toolbar;
    private TextView txtToolbarText;

    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);


        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
            id = sharedPreferences.getInt("id",0);
        }

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Transport Route");


        //main recyclerview
        recyclerView = findViewById(R.id.transportRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllTransports();

    }


    void getAllTransports(){

        transports.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.STUDENT_TRANSPORT_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");



                        for(int i = 0 ; i < array.length() ; i++){

                            String route = array.getJSONObject(i).getString("route");
                            String vehicle_no = array.getJSONObject(i).getString("vehicle_no");
                            String vehicle_model = array.getJSONObject(i).getString("vehicle_model");
                            String made_year = array.getJSONObject(i).getString("made_year");
                            String driver_name = array.getJSONObject(i).getString("driver_name");
                            String mobile = array.getJSONObject(i).getString("mobile");
                            String driving_license = array.getJSONObject(i).getString("driving_license");
                            String driver_contact = array.getJSONObject(i).getString("mobile");

                            Transport transport = new Transport(route,vehicle_no,vehicle_model,made_year,driver_name,mobile,driving_license,driver_contact);
                            transports.add(transport);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(transports.size() > 0){

                    adapter = new TransportAdapetr(transports,TransportActivity.this);
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
