package com.infix.edu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.adapter.StudentListAdapter;
import com.infix.edu.model.Tstudent;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentFragment extends Fragment {

    private RecyclerView studentRecyclerView;
    private StudentListAdapter adapter;
    final ArrayList<Tstudent> students = new ArrayList<>();
    private ProgressBar progressBar;
    public StudentFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        studentRecyclerView = view.findViewById(R.id.studentRecycler);
        studentRecyclerView.setHasFixedSize(true);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getAllStudent();

        return view;
    }

    public void getAllStudent(){


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.STUDENT_LIST, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.getBoolean("success")){


                                JSONArray jsonArray = response.getJSONObject("data").getJSONArray("student_list");

                                for(int i = 0 ; i < 10 ; i++){


                                    int id = jsonArray.getJSONObject(i).getInt("user_id");
                                    String class_name = jsonArray.getJSONObject(i).getString("class_name");
                                    String section_name = jsonArray.getJSONObject(i).getString("section_name");
                                    int roll_no = jsonArray.getJSONObject(i).getInt("roll_no");
                                    String full_name = jsonArray.getJSONObject(i).getString("full_name");
                                    String student_photo = jsonArray.getJSONObject(i).getString("student_photo");
                                    Tstudent tstudent = new Tstudent(full_name,roll_no,class_name,section_name,student_photo,id);
                                    students.add(tstudent);

                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(students.size() > 0){

                            adapter = new StudentListAdapter(students,getActivity(),"admin");
                            studentRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue req = Volley.newRequestQueue(getActivity());
        req.add(request);

    }


}
