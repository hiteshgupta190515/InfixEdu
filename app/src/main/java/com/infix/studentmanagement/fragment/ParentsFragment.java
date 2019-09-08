package com.infix.studentmanagement.fragment;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.adapter.PersonalAdapter;
import com.infix.studentmanagement.model.PersonalData;
import com.infix.studentmanagement.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentsFragment extends Fragment {


    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private PersonalAdapter adapter;

    private String fatherName;
    private String fatherPhone;
    private String fatherOcupation;
    private String motherName;
    private String motherPhone;
    private String motherOcupation;
    private String gurdianName;
    private String gurdianPhone;
    private String gurdianOcupation;
    private String gurdianRelation;
    private String gurdianEmail;
    private String email;
    private String password;
    private String url;
    private int id;


    private SharedPreferences sharedPreferences;

    public ParentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_parents, container, false);

        recyclerView = v.findViewById(R.id.parentsRecyclerview);
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

        getParents(url);

        // Inflate the layout for this fragment
        return v;
    }

    private void getParents(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                try {
                    if(response.getBoolean("success")){

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");

                        fatherName = detailsObj.getString("fathers_name");
                        fatherOcupation = detailsObj.getString("fathers_occupation");
                        fatherPhone = detailsObj.getString("fathers_mobile");
                        motherName = detailsObj.getString("mothers_name");
                        motherPhone = detailsObj.getString("mothers_mobile");
                        motherOcupation = detailsObj.getString("mothers_occupation");
                        gurdianName = detailsObj.getString("guardians_name");
                        gurdianPhone = detailsObj.getString("guardians_mobile");
                        gurdianOcupation = detailsObj.getString("guardians_occupation");
                        gurdianEmail = detailsObj.getString("guardians_email");
                        gurdianRelation = detailsObj.getString("guardians_relation");

                        personalData.add(new PersonalData("Father’s name",fatherName));
                        personalData.add(new PersonalData("Father’s phone",fatherPhone));
                        personalData.add(new PersonalData("Father’s occupation",fatherOcupation));
                        personalData.add(new PersonalData("Mother’s name",motherName));
                        personalData.add(new PersonalData("Mother’s phone",motherPhone));
                        personalData.add(new PersonalData("Mother’s occupation",motherOcupation));
                        personalData.add(new PersonalData("guardians name",gurdianName));
                        personalData.add(new PersonalData("guardians email",gurdianEmail));
                        personalData.add(new PersonalData("guardians occupation",gurdianOcupation));
                        personalData.add(new PersonalData("guardians phone",gurdianPhone));
                        personalData.add(new PersonalData("guardians relation",gurdianRelation));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new PersonalAdapter(personalData,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

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
