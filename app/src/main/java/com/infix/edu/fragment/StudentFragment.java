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
    final ArrayList<Student> students = new ArrayList<>();
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


                                JSONArray jsonArray = response.getJSONObject("data").getJSONArray("students");

                                for(int i = 0 ; i < jsonArray.length() ; i++){


                                    int id = jsonArray.getJSONObject(i).getInt("id");
                                    int school_id = jsonArray.getJSONObject(i).getInt("school_id");
                                    int class_id = jsonArray.getJSONObject(i).getInt("class_id");
                                    int student_category_id = jsonArray.getJSONObject(i).getInt("student_category_id");
                                    int section_id = jsonArray.getJSONObject(i).getInt("section_id");
                                    int session_id = jsonArray.getJSONObject(i).getInt("session_id");
                                    int user_id = jsonArray.getJSONObject(i).getInt("user_id");
                                    int parent_id = jsonArray.getJSONObject(i).getInt("parent_id");
                                    int gender_id = jsonArray.getJSONObject(i).getInt("gender_id");
                                    int admission_no = jsonArray.getJSONObject(i).getInt("admission_no");
                                    int roll_no = jsonArray.getJSONObject(i).getInt("roll_no");
                                    String first_name = jsonArray.getJSONObject(i).getString("first_name");
                                    String last_name = jsonArray.getJSONObject(i).getString("last_name");
                                    String full_name = jsonArray.getJSONObject(i).getString("full_name");
                                    String date_of_birth = jsonArray.getJSONObject(i).getString("date_of_birth");
                                    String caste = jsonArray.getJSONObject(i).getString("caste");
                                    String email = jsonArray.getJSONObject(i).getString("email");
                                    String mobile = jsonArray.getJSONObject(i).getString("mobile");
                                    String admission_date = jsonArray.getJSONObject(i).getString("admission_date");
                                    String student_photo = jsonArray.getJSONObject(i).getString("student_photo");
                                    String height = jsonArray.getJSONObject(i).getString("height");
                                    String weight = jsonArray.getJSONObject(i).getString("weight");
                                    String current_address = jsonArray.getJSONObject(i).getString("current_address");
                                    String permanent_address = jsonArray.getJSONObject(i).getString("permanent_address");
                                    int bloodgroup_id = jsonArray.getJSONObject(i).getInt("bloodgroup_id");
                                    int religion_id = jsonArray.getJSONObject(i).getInt("religion_id");

                                    Student student = new Student(id,school_id,class_id,student_category_id,section_id,session_id,
                                            user_id,parent_id,gender_id,admission_no,roll_no,first_name,last_name,full_name,
                                            date_of_birth,caste,email,mobile,admission_date,student_photo,height,weight,current_address,permanent_address,bloodgroup_id,religion_id);

                                    students.add(student);

                                   // Toast.makeText(getActivity(),students.size()+"", Toast.LENGTH_SHORT).show();

                              // students.add(new Student(full_name,email,student_photo));

                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(students.size() > 0){

                            adapter = new StudentListAdapter(students,getActivity());
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
