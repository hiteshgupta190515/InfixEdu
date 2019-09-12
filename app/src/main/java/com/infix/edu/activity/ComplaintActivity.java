package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.adapter.ComplaintAdapter;
import com.infix.edu.model.Complaint;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComplaintActivity extends AppCompatActivity {

    private ArrayList<Complaint> complaints = new ArrayList<>();
    private RecyclerView recyclerView;
    private ComplaintAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        recyclerView = findViewById(R.id.complaintRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllComplaint();

    }

    void getAllComplaint(){

        complaints.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.COMPLAINT_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("complaints");



                        for(int i = 0 ; i < array.length() ; i++){

                            int id = array.getJSONObject(i).getInt("id");
                            int type_id = array.getJSONObject(i).getInt("complaint_type");
                            int source_id = array.getJSONObject(i).getInt("complaint_source");
                            String byName = array.getJSONObject(i).getString("complaint_by");
                            String phone = array.getJSONObject(i).getString("phone");
                            String date = array.getJSONObject(i).getString("date");
                            String descrip = array.getJSONObject(i).getString("description");
                            String action_taken = array.getJSONObject(i).getString("action_taken");
                            String assigned = array.getJSONObject(i).getString("assigned");
                            String url = array.getJSONObject(i).getString("file");
                            int created_by = array.getJSONObject(i).getInt("created_by");


                            Complaint complaint = new Complaint(id,type_id,source_id,created_by,byName,action_taken,phone,date,
                                    descrip,url,assigned);

                            complaints.add(complaint);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(complaints.size() > 0){

                    adapter = new ComplaintAdapter(complaints,getApplicationContext());
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
