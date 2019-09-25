package com.infix.edu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infix.edu.R;
import com.infix.edu.myconfig.Helper;

public class ContentAddActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String strRadio;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add);

        radioGroup = findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.all_admin:

                        radioButton = findViewById(R.id.all_admin);

                        strRadio  = "admin";
                        break;

                    case R.id.student:

                        radioButton = findViewById(R.id.student);

                        strRadio  = "student";

                        showSelectOption();

                        break;

                }
            }

        });

    }

    private void showSelectOption(){


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ContentAddActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(ContentAddActivity.this).inflate(R.layout.student_content_layout, null);

        final RadioButton radioButton = mView.findViewById(R.id.available);
        final Spinner spnClass = mView.findViewById(R.id.choose_classes_spinner);
        final Spinner spnSection = mView.findViewById(R.id.choose_section_spinner);

        Helper helper = new Helper();
        int id = helper.getClassAndSectionName(spnClass,spnSection,this);

        Toast.makeText(getApplicationContext(),id+"",Toast.LENGTH_SHORT).show();

       radioButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!isChecked) {
                   isChecked = true;

                   spnClass.setVisibility(View.GONE);
                   spnSection.setVisibility(View.GONE);

               }else{
                   isChecked = false;
                   radioButton.setChecked(isChecked);

                   spnClass.setVisibility(View.VISIBLE);
                   spnSection.setVisibility(View.VISIBLE);

               }
           }
       });


        alertBuilder.setView(mView);
        AlertDialog dialog = alertBuilder.create();

        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        mView.setMinimumWidth((int)(displayRectangle.width()));
        mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();


    }

}
