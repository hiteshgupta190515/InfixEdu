package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.studentmanagement.R;
import com.example.studentmanagement.fragment.PersonalFragment;
import com.example.studentmanagement.fragment.StudentFragment;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);


        profileFragment = new PersonalFragment();
        replaceFragment(profileFragment);

    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_fragmengt_container,fragment);
        transaction.commit();
    }

}
