package com.infix.edu.fragment;

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
import com.infix.edu.adapter.PersonalAdapter;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.PersonalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PersonalFragment extends Fragment{

    private String fatherName;
    private String motherName;
    private String religion;
    private String presentAddress;
    private String permanentAddress;
    private String bloodGroup;
    private String studentEmail;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String password;
    private String url;
    private int id;

    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private PersonalAdapter adapter;

    private SharedPreferences sharedPreferences;



    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal, container, false);


        recyclerView = v.findViewById(R.id.personalRecyclerview);
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

        getPersonal(url);


        return v;
    }

    private void getPersonal(String url){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");

                                fatherName = detailsObj.getString("fathers_name");
                                motherName = detailsObj.getString("mothers_name");
                                presentAddress = detailsObj.getString("current_address");
                                permanentAddress = detailsObj.getString("permanent_address");
                                religion = response.getJSONObject("data").getJSONObject("religion").getString("name");
                                bloodGroup = response.getJSONObject("data").getJSONObject("blood_group").getString("name");
                                dateOfBirth = detailsObj.getString("date_of_birth");
                                phone = detailsObj.getString("mobile");
                                email = detailsObj.getString("email");

                        personalData.add(new PersonalData("Date Of Birth",dateOfBirth));
                        personalData.add(new PersonalData("Religion",religion));
                        personalData.add(new PersonalData("Phone Number",phone));
                        personalData.add(new PersonalData("Email address",email));
                        personalData.add(new PersonalData("Present address",presentAddress));
                        personalData.add(new PersonalData("Parmanent address",permanentAddress));
                        personalData.add(new PersonalData("Father’s name",fatherName));
                        personalData.add(new PersonalData("Mother’s name",motherName));
                        personalData.add(new PersonalData("Blood group",bloodGroup));

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
