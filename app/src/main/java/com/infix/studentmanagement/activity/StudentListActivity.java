package com.infix.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.adapter.OptionAdapter;
import com.infix.studentmanagement.fragment.StudentFragment;

public class StudentListActivity extends AppCompatActivity implements OptionAdapter.ClickListener {

    private Fragment mainFragment;
    private RecyclerView recyclerViewOption;
    private StaggeredGridLayoutManager gridLayoutManager;
    private String[] names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


        recyclerViewOption = findViewById(R.id.studentRecyclerOptions);
        recyclerViewOption.setHasFixedSize(true);
        gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewOption.setLayoutManager(gridLayoutManager);

        names = getResources().getStringArray(R.array.student_functions_name);
        OptionAdapter optionAdapter = new OptionAdapter(names,StudentListActivity.this);
        optionAdapter.setClickListener(StudentListActivity.this);
        recyclerViewOption.setAdapter(optionAdapter);

        mainFragment = new StudentFragment();
        replaceFragment(mainFragment);

       // Toast.makeText(getApplicationContext(),"show",Toast.LENGTH_LONG).show();

    }

    private void replaceFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.student_container,fragment);
        transaction.commit();
    }

    @Override
    public void itemClicked(String s, int position, View view) {

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();

    }
}
