package com.infix.edu.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.adapter.PersonalAdapter;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.PersonalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransportFragment extends Fragment {

    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private PersonalAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String name,model,no,des;
    private String email,password;
    private String url;
    private int id;

    public TransportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transport, container, false);

        recyclerView = v.findViewById(R.id.transportRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sharedPreferences = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("email",null);
        password = sharedPreferences.getString("password",null);

        //getting id role_id url based on parents and child
        if(getActivity().getIntent().getIntExtra("id",0) != 0){
            id = getActivity().getIntent().getIntExtra("id",0);
            url = MyConfig.getChildren(id);
        }else{
            id = sharedPreferences.getInt("id", 0);
            url = MyConfig.getLoginUrl(email, password);
        }

        getDriver(url);

        Log.d("transport",url);

        return v;
    }

    private void getDriver(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")) {

                        JSONObject driverObj;
                        driverObj = response.getJSONObject("data").optJSONObject("transport");

                        if (driverObj != null){

                        name = driverObj.getString("driver_name");
                        no = driverObj.getString("vehicle_no");
                        model = driverObj.getString("vehicle_model");
                        des = driverObj.getString("note");

                        personalData.add(new PersonalData("Driver's Name", name));
                        personalData.add(new PersonalData("Car No", no));
                        personalData.add(new PersonalData("Car Model", model));
                        personalData.add(new PersonalData("Car Info", des));

                    }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(personalData.size() > 0) {
                    adapter = new PersonalAdapter(personalData, getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "server error!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("email", email);
                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

}
