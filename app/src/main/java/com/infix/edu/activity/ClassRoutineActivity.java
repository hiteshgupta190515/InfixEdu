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
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.adapter.RoutineAdapter;
import com.infix.edu.model.Routine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClassRoutineActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private RecyclerView recySaturday,recySunday,recyMonday,recyTuesday,recyWednesday,recyThursday,recyFriday;
    private String stringSaturday,stringSunday,stringMonday,stringTuesday,stringWednesday,stringThursday,stringFriday;
    private ArrayList<Routine> saturs,suns,mons,tues,weds,thurs,fris;
    private RoutineAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_routine);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            id = sharedPreferences.getInt("id",0);
        }

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Routine");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);

        initRecyclerview();

        getAllRoutine(id);

        MyConfig.getProfileImage(profile_image_url,profile,ClassRoutineActivity.this);

    }

    private void initRecyclerview() {

        recySaturday = findViewById(R.id.recyclerSaturday);
        recySunday = findViewById(R.id.recyclerSunday);
        recyMonday = findViewById(R.id.recyclerMonday);
        recyTuesday = findViewById(R.id.recyclerTuesday);
        recyThursday = findViewById(R.id.recyclerThursday);
        recyWednesday = findViewById(R.id.recyclerWednesday);
        recyFriday = findViewById(R.id.recyclerFriday);

        recySaturday.setHasFixedSize(true);
        recySaturday.setLayoutManager(new LinearLayoutManager(this));

        recySunday.setHasFixedSize(true);
        recySunday.setLayoutManager(new LinearLayoutManager(this));

        recyMonday.setHasFixedSize(true);
        recyMonday.setLayoutManager(new LinearLayoutManager(this));

        recyTuesday.setHasFixedSize(true);
        recyTuesday.setLayoutManager(new LinearLayoutManager(this));

        recyThursday.setHasFixedSize(true);
        recyThursday.setLayoutManager(new LinearLayoutManager(this));

        recyWednesday.setHasFixedSize(true);
        recyWednesday.setLayoutManager(new LinearLayoutManager(this));

        recyFriday.setHasFixedSize(true);
        recyFriday.setLayoutManager(new LinearLayoutManager(this));



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

                        JSONArray satArray = response.getJSONObject("data").getJSONArray("Saturday");
                        JSONArray sunArray = response.getJSONObject("data").getJSONArray("Sunday");
                        JSONArray monArray = response.getJSONObject("data").getJSONArray("Monday");
                        JSONArray tuesArray = response.getJSONObject("data").getJSONArray("Tuesday");
                        JSONArray wedsArray = response.getJSONObject("data").getJSONArray("Wednesday");
                        JSONArray thursArray = response.getJSONObject("data").getJSONArray("Thursday");
                        JSONArray frisArray = response.getJSONObject("data").getJSONArray("Friday");

                        getRoutineFromJson(satArray,saturs,stringSaturday);
                        getRoutineFromJson(sunArray,suns,stringSunday);
                        getRoutineFromJson(monArray,mons,stringMonday);
                        getRoutineFromJson(tuesArray,tues,stringTuesday);
                        getRoutineFromJson(wedsArray,weds,stringWednesday);
                        getRoutineFromJson(thursArray,thurs,stringThursday);
                        getRoutineFromJson(frisArray,fris,stringFriday);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

    public ArrayList<Routine> getRoutineFromJson(JSONArray array,ArrayList<Routine> routines,String week) throws JSONException {

        routines.clear();

        routines.add(new Routine("Time","Subject","Room",week));


        for(int i = 0 ; i < array.length() ; i++){

            String start_time = array.getJSONObject(i).getString("start_time");
            String end_time = array.getJSONObject(i).getString("end_time");
            String subject = array.getJSONObject(i).getString("subject_name");
            String room = array.getJSONObject(i).getString("room_no");

            routines.add(new Routine(getAmPm(start_time),getAmPm(end_time),subject,room,week));

        }

        adapter = new RoutineAdapter(routines,this);

        switch (week){

            case "Saturday":
                recySaturday.setAdapter(adapter);
                break;
            case "Sunday":
                recySunday.setAdapter(adapter);
                break;
            case "Monday":
                recyMonday.setAdapter(adapter);
                break;
            case "Tuesday":
                recyTuesday.setAdapter(adapter);
                break;
            case "Wednesday":
                recyWednesday.setAdapter(adapter);
                break;
            case "Thursday":
                recyThursday.setAdapter(adapter);
                break;
            case "Friday":
                recyFriday.setAdapter(adapter);
                break;

        }

        adapter.notifyDataSetChanged();

        return routines;
    }

    String getAmPm(String time){

        String[] parts = time.split(":");
        String part1 = parts[0];
        String part2 = parts[1];

        int hr = Integer.parseInt(part1);
        int min = Integer.parseInt(part2);

        if(hr <= 12){
            return hr+":"+min+" ÄM ";
        }else{
            return hr+":"+min+" PM ";
        }

    }

}
