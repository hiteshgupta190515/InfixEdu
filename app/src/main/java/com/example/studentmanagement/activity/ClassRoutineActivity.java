package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.InventoryAdapter;
import com.example.studentmanagement.adapter.RoutineAdapter;
import com.example.studentmanagement.model.Inventory;
import com.example.studentmanagement.model.Routine;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassRoutineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private RecyclerView recySaturday,recySunday,recyMonday,recyTuesday,recyWednesday,recyThursday,recyFriday;
    private String stringSaturday,stringSunday,stringMonday,stringTuesday,stringWednesday,stringThursday,stringFriday;
    private ArrayList<Routine> saturs,suns,mons,tues,weds,thurs,fris;
    private RoutineAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_routine);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        txtToolbarText.setText("Class Routine");

        initRecyclerview();
        getAllRoutine(id);

    }

    private void initRecyclerview() {

        recySaturday = findViewById(R.id.recyclerSaturday);
        recySunday = findViewById(R.id.recyclerSunday);
        recyMonday = findViewById(R.id.recyclerMonday);
        recyTuesday = findViewById(R.id.recyclerTuesday);
        recyThursday = findViewById(R.id.recyclerThursday);
        recyWednesday = findViewById(R.id.recyclerWednesday);
        recyFriday = findViewById(R.id.recyclerFriday);

        stringSaturday = "Saturday";
        stringSunday = "Sunday";
        stringMonday = "Monday";
        stringTuesday = "Tuesday";
        stringThursday = "Wednesday";
        stringWednesday = "Thursday";
        stringFriday = "Friday";

        saturs = new ArrayList<>();
        suns = new ArrayList<>();
        mons = new ArrayList<>();
        tues = new ArrayList<>();
        thurs = new ArrayList<>();
        weds = new ArrayList<>();
        fris = new ArrayList<>();

    }

    void getAllRoutine(int id){


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getRoutineUrl(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("items");



                        for(int i = 0 ; i < array.length() ; i++){

                            String name = array.getJSONObject(i).getString("item_name");
                            int category_name = array.getJSONObject(i).getInt("category_name");
                            String des = array.getJSONObject(i).getString("description");
                            int total_in_stock = array.getJSONObject(i).getInt("total_in_stock");
                            int id = array.getJSONObject(i).getInt("id");
                            int school_id = array.getJSONObject(i).getInt("school_id");



                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(saturs.size() > 0){

                    adapter = new RoutineAdapter(saturs,getApplicationContext());
                    recySaturday.setAdapter(adapter);
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
