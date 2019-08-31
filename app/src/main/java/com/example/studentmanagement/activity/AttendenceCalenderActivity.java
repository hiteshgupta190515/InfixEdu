package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.AttendanceAdapter;
import com.example.studentmanagement.adapter.TransportAdapetr;
import com.example.studentmanagement.model.Attendence;
import com.example.studentmanagement.model.Transport;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AttendenceCalenderActivity extends AppCompatActivity {

    private ArrayList<Attendence> attendences = new ArrayList<>();
    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;


    private Toolbar toolbar;
    private TextView txtToolbarText;

    private SharedPreferences sharedPreferences;
    private int id;

    private int present = 0;
    private int absent = 0;
    private int late = 0;
    private int holiday = 0;
    private int halfday = 0;

    private TextView txtPresent;
    private TextView txtAbsent;
    private TextView txtLate;
    private TextView txtHoliday;
    private TextView txtHalfDay;

    int month;
    int year;

    private TextView page_title;
    private ImageView left_image,right_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_calender);

        txtPresent = findViewById(R.id.present);
        txtAbsent = findViewById(R.id.absent);
        txtLate = findViewById(R.id.late);
        txtHalfDay = findViewById(R.id.halfday);
        txtHoliday = findViewById(R.id.holiday);
        page_title = findViewById(R.id.txtPageTitle);
        left_image = findViewById(R.id.preButton);
        right_image = findViewById(R.id.nextButton );


        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Attendance");


        //main recyclerview
        recyclerView = findViewById(R.id.calenderRecycler);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(this,7);
        recyclerView.setLayoutManager(manager);


        getAttendanceCalender(id,month,year);

        left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(month >= 1){

                    present = 0;
                    absent = 0;
                    late = 0;
                    holiday = 0;
                    halfday = 0;

                    --month;


                    getAttendanceCalender(id,month,year);
                }


            }
        });

        right_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(month <= 12){

                    present = 0;
                    absent = 0;
                    late = 0;
                    holiday = 0;
                    halfday = 0;

                    ++month;

                    getAttendanceCalender(id,month,year);
                }

            }
        });

    }



    void getAttendanceCalender(int i, final int m, final int y){

        attendences.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentAttendence(i,m,y), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("attendances");
                        JSONObject prev_month_object = response.getJSONObject("data").getJSONObject("previousMonthDetails");

                        int total_day = response.getJSONObject("data").getInt("days");

                        int p_day = prev_month_object.getInt("day");
                        String p_week_name = prev_month_object.getString("week_name");

                        for(int j = MyConfig.getWeekDay(p_week_name) ; j >= 0 ; j--){

                            attendences.add(new Attendence(String.valueOf(p_day - j),j+1));

                        }

//                        for(int a = 1 ; a <= total_day ; a++){
//
//                            if(a < 10){
//                                attendences.add(new Attendence("0"+String.valueOf(a),1));
//                            }else{
//                                attendences.add(new Attendence(String.valueOf(a),1));
//                            }
//                        }

                        for(int k = 0 ; k < array.length() ; k++){

                            String date = array.getJSONObject(k).getString("attendance_date");
                            String type = array.getJSONObject(k).getString("attendance_type");

                            String[] day = date.split("-");
                            String currentDay = day[2];


                            Attendence attendence = new Attendence(currentDay,type);
                            attendences.add(attendence);


                            if(type.equalsIgnoreCase("A")){

                               absent++;

                            }else if(type.equalsIgnoreCase("P")){

                               present++;

                            }else if(type.equalsIgnoreCase("L")){

                               late++;

                            }else if(type.equalsIgnoreCase("H")){

                               halfday++;

                            }else if(type.equalsIgnoreCase("F")){

                               holiday++;

                            }

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(attendences.size() > 0){

                    adapter = new AttendanceAdapter(attendences,AttendenceCalenderActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    txtPresent.setText(present+" Days");
                    txtAbsent.setText(absent+" Days");
                    txtLate.setText(late+" Days");
                    txtHalfDay.setText(halfday+" Days");
                    txtHoliday.setText(holiday+" Days");

                    page_title.setText(MyConfig.getMonth(month)+" "+year);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"server problem", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }


}
