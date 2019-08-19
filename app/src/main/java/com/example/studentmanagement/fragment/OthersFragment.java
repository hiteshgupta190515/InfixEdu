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
import com.example.studentmanagement.model.PersonalData;

import java.util.ArrayList;

public class OthersFragment extends Fragment {

    private ArrayList<PersonalData> personalData = new ArrayList<>();
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private PersonalAdapter adapter;

    private String height;
    private String weight;
    private String castle;
    private String nationalId;

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

        height = sharedPreferences.getString("height",null);
        weight = sharedPreferences.getString("weight",null);
        castle = sharedPreferences.getString("castle",null);
        nationalId = sharedPreferences.getString("nationalId",null);

        personalData.add(new PersonalData("Height",height));
        personalData.add(new PersonalData("Weight",weight));
        personalData.add(new PersonalData("Castle",castle));
        personalData.add(new PersonalData("National Id",nationalId));


        adapter = new PersonalAdapter(personalData,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return v;
    }



}
