package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.studentmanagement.R;
import com.example.studentmanagement.fragment.StudentFragment;

public class StudentListActivity extends AppCompatActivity {

    private Fragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mainFragment = new StudentFragment();
        replaceFragment(mainFragment);

       // Toast.makeText(getApplicationContext(),"show",Toast.LENGTH_LONG).show();

    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.student_container,fragment);
        transaction.commit();
    }

}
