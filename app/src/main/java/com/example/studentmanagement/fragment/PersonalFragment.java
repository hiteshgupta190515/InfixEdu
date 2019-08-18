package com.example.studentmanagement.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmanagement.R;


public class PersonalFragment extends Fragment {

    private String name;
    private String fatherName;
    private String motherName;
    private String section;
    private String roll;
    private String className;
    private String admission_no;
    private String religion;
    private String presentAddress;
    private String permanentAddress;
    private String bloodGroup;
    private String studentEmail;
    private String dateOfBirth;

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

        sharedPreferences = getActivity().getSharedPreferences("default", Context.MODE_PRIVATE);

        name = sharedPreferences.getString("full_name",null);
        fatherName = sharedPreferences.getString("full_name",null);


        return v;
    }


}
