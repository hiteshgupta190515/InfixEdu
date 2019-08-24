package com.example.studentmanagement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.PersonalAdapter;
import com.example.studentmanagement.model.PersonalData;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OthersFragment extends Fragment {

    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private PersonalAdapter adapter;

    private String height;
    private String weight;
    private String castle;
    private String nationalId;

    private String email,password;

    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_others, container, false);

        recyclerView = v.findViewById(R.id.othersRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        sharedPreferences = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);

        email = sharedPreferences.getString("email",null);
        password = sharedPreferences.getString("password",null);

        getOthers(email,password);

        return v;
    }

    private void getOthers(final String email, final String password){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyConfig.getLoginUrl(email, password), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")) {

                        JSONObject detailsObj;
                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");

                        if (detailsObj != null){

                            height = detailsObj.getString("height");
                            weight = detailsObj.getString("weight");
                            castle = detailsObj.getString("caste");
                            nationalId = detailsObj.getString("national_id_no");

                            personalData.add(new PersonalData("Height",height));
                            personalData.add(new PersonalData("Weight",weight));
                            personalData.add(new PersonalData("Castle",castle));
                            personalData.add(new PersonalData("National Id",nationalId));

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
                Toast.makeText(getActivity(), "Invalid username/password", Toast.LENGTH_LONG).show();
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
