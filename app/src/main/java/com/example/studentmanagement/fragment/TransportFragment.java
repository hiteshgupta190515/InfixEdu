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

public class TransportFragment extends Fragment {

    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private PersonalAdapter adapter;
    private SharedPreferences sharedPreferences;
    private String name,model,no,des;

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



        name = sharedPreferences.getString("driverName",null);
        no = sharedPreferences.getString("driverCarNo",null);
        model = sharedPreferences.getString("drivercarModel",null);
        des = sharedPreferences.getString("driverDetails",null);

        personalData.add(new PersonalData("Driver's Name",name));
        personalData.add(new PersonalData("Car No",no));
        personalData.add(new PersonalData("Car Model",model));
        personalData.add(new PersonalData("Car Info",des));


        adapter = new PersonalAdapter(personalData,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return v;
    }

}
