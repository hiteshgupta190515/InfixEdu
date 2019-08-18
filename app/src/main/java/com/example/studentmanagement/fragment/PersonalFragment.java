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

import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.PersonalAdapter;
import com.example.studentmanagement.adapter.VisitorAdapter;
import com.example.studentmanagement.model.PersonalData;
import com.example.studentmanagement.model.Visitor;

import java.util.ArrayList;


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
    private String phone;

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

        //get the from sharedPref
        name = sharedPreferences.getString("name",null);
        fatherName = sharedPreferences.getString("fatherName",null);
        motherName = sharedPreferences.getString("motherName",null);
        section = sharedPreferences.getString("section",null);
        roll = sharedPreferences.getString("roll",null);
        className = sharedPreferences.getString("className",null);
        admission_no = sharedPreferences.getString("admissionNo",null);
        religion = sharedPreferences.getString("religion",null);
        presentAddress = sharedPreferences.getString("presentAd",null);
        permanentAddress = sharedPreferences.getString("permanentAd",null);
        bloodGroup = sharedPreferences.getString("bloodGroup",null);
        studentEmail = sharedPreferences.getString("email",null);
        dateOfBirth = sharedPreferences.getString("dateOfBirth",null);
        phone = sharedPreferences.getString("phone",null);

        personalData.add(new PersonalData("Date Of Birth",dateOfBirth));
        personalData.add(new PersonalData("Religion",religion));
        personalData.add(new PersonalData("Phone Number",phone));
        personalData.add(new PersonalData("Email address",studentEmail));
        personalData.add(new PersonalData("Present address",presentAddress));
        personalData.add(new PersonalData("Parmanent address",permanentAddress));
        personalData.add(new PersonalData("Father’s name",fatherName));
        personalData.add(new PersonalData("Mother’s name",motherName));
        personalData.add(new PersonalData("Blood group",bloodGroup));


        adapter = new PersonalAdapter(personalData,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return v;
    }


}
