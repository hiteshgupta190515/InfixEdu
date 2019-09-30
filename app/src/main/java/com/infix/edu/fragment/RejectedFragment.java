package com.infix.edu.fragment;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.adapter.LeaveListAdapter;
import com.infix.edu.model.LeaveList;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RejectedFragment extends Fragment {

    private ArrayList<LeaveList> leaves = new ArrayList<>();
    private RecyclerView recyclerView;
    private LeaveListAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int role_id;


    public RejectedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rejected, container, false);
        sharedPreferences = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);
        role_id = sharedPreferences.getInt("role", 0);


        //main recyclerview
        recyclerView = v.findViewById(R.id.rejeted_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getAllRejectedLeaves();

        // Inflate the layout for this fragment
        return v;
    }


    void getAllRejectedLeaves(){

        leaves.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.REJECTED_LEAVE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("reject_request");



                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("type");
                            String description = array.getJSONObject(i).getString("reason");
                            String applay_date = array.getJSONObject(i).getString("apply_date");
                            String leave_from = array.getJSONObject(i).getString("leave_from");
                            String leave_to = array.getJSONObject(i).getString("leave_to");
                            String approve_status = array.getJSONObject(i).getString("approve_status");
                            int id = array.getJSONObject(i).getInt("id");

                            LeaveList leaveList = new LeaveList(title,applay_date,leave_from,leave_to,approve_status,id,role_id,description);
                            leaves.add(leaveList);
                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(leaves.size() > 0){

                    adapter = new LeaveListAdapter(leaves,getActivity());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"server error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(getActivity());
        req.add(request);

    }


}
