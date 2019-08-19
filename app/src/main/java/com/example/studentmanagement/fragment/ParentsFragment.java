package com.example.studentmanagement.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.PersonalAdapter;
import com.example.studentmanagement.model.PersonalData;

import java.util.ArrayList;

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

        fatherName = sharedPreferences.getString("fatherName",null);
        fatherPhone = sharedPreferences.getString("fatherPhone",null);
        fatherOcupation = sharedPreferences.getString("fatherOccupation",null);
        motherName = sharedPreferences.getString("motherName",null);
        motherPhone = sharedPreferences.getString("motherPhone",null);
        motherOcupation = sharedPreferences.getString("motherOcupation",null);
        gurdianName = sharedPreferences.getString("gurdName",null);
        gurdianPhone = sharedPreferences.getString("gurdPhone",null);
        gurdianEmail = sharedPreferences.getString("gurdEmail",null);
        gurdianOcupation = sharedPreferences.getString("gurdOcupation",null);
        gurdianRelation = sharedPreferences.getString("gurdRelation",null);


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


        adapter = new PersonalAdapter(personalData,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return v;
    }

}
