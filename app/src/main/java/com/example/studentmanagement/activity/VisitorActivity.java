package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.AutoCompleteBookAdapter;
import com.example.studentmanagement.adapter.BookAdapter;
import com.example.studentmanagement.adapter.VisitorAdapter;
import com.example.studentmanagement.model.Book;
import com.example.studentmanagement.model.Visitor;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisitorActivity extends AppCompatActivity {

    private ArrayList<Visitor> visitors = new ArrayList<>();
    private RecyclerView recyclerView;
    private VisitorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        recyclerView = findViewById(R.id.visitorRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllVisitors();

    }

    void getAllVisitors(){

        visitors.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.VISITOR_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");



                        for(int i = 0 ; i < array.length() ; i++){

                            int id = array.getJSONObject(i).getInt("id");
                            String visitor_id = array.getJSONObject(i).getString("visitor_id");
                            String name = array.getJSONObject(i).getString("name");
                            String phone = array.getJSONObject(i).getString("phone");
                            String no_of_person = array.getJSONObject(i).getString("no_of_person");
                            String purpose = array.getJSONObject(i).getString("purpose");
                            String date = array.getJSONObject(i).getString("date");
                            String in_time = array.getJSONObject(i).getString("in_time");
                            String out_time = array.getJSONObject(i).getString("out_time");
                            String url = array.getJSONObject(i).getString("file");


                            Visitor visitor = new Visitor(id,visitor_id,name,phone,purpose,
                                    date,no_of_person,url,in_time,out_time);

                            visitors.add(visitor);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(visitors.size() > 0){

                    adapter = new VisitorAdapter(visitors,getApplicationContext());
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
