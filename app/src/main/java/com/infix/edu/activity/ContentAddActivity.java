package com.infix.edu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;

public class ContentAddActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String strRadio;
    private boolean isChecked = false;
    private ArrayList<SearchData> classArray = new ArrayList<>();
    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<SearchData> sectionArray = new ArrayList<>();
    private ArrayList<String> sections = new ArrayList<>();
    private int class_id;
    private int section_id;
    private String class_name;
    private String section_name;
    private String update_date;
    private LinearLayout update_date_linear;
    private RelativeLayout attach_file;
    private TextView txt_attach_file;
    private MediaType FORM = MediaType.parse("multipart/form-data");
    private static final int STORAGE_PERMISSION_CODE = 123;
    private boolean isPermissionGranted = false;
    private Uri path;
    private int year, month, day;
    private TextView txtUpdatedate;
    private DatePickerDialog datePickerDialog;
    private Helper helper = new Helper();
    private Spinner spn_content_type;
    private String[] content_type;
    private String contentStr;
    private EditText et_title;
    private EditText et_reason;
    private Button btn_save_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_add);

        radioGroup = findViewById(R.id.radio_group);
        update_date_linear = findViewById(R.id.update_date);
        update_date_linear.setOnClickListener(this);
        attach_file = findViewById(R.id.attach_file);
        attach_file.setOnClickListener(this);
        txtUpdatedate = findViewById(R.id.update_date_text);
        txt_attach_file = findViewById(R.id.txt_attach_file);
        spn_content_type = findViewById(R.id.choose_content_type_spinner);
        et_title = findViewById(R.id.content_title);
        et_reason = findViewById(R.id.reason_textbox);
        btn_save_content = findViewById(R.id.btn_save_content);
        btn_save_content.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        classArray = helper.getClassData(this);
        sectionArray = helper.getSectionData(this);

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

        content_type = getResources().getStringArray(R.array.content_type_name);
        ArrayAdapter adapter = new ArrayAdapter(ContentAddActivity.this, R.layout.spinner_row_layout, R.id.spn_text, content_type);
        spn_content_type.setAdapter(adapter);

        spn_content_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 0) {
                    contentStr = content_type[i].substring(0,2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, final Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                path = result.getData();
                txt_attach_file.setText(helper.getPathFromUri(path,this));
            }
        }
    }

    private void showSelectOption(){


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ContentAddActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(ContentAddActivity.this).inflate(R.layout.student_content_layout, null);

        final RadioButton radioButton = mView.findViewById(R.id.available);
        final Spinner spnClass = mView.findViewById(R.id.choose_classes_spinner);
        final Spinner spnSection = mView.findViewById(R.id.choose_section_spinner);

        for(SearchData s: classArray){
            classes.add(s.getKey());
        }
        for(SearchData s: sectionArray){
            sections.add(s.getKey());
        }


        ArrayAdapter adapter1 = new ArrayAdapter(ContentAddActivity.this, R.layout.spinner_row_layout, R.id.spn_text, classes);
        spnClass.setAdapter(adapter1);

        ArrayAdapter adapter2 = new ArrayAdapter(ContentAddActivity.this, R.layout.spinner_row_layout, R.id.spn_text, sections);
        spnSection.setAdapter(adapter2);

       radioButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(!isChecked) {
                   isChecked = true;

                   spnClass.setVisibility(View.GONE);
                   spnSection.setVisibility(View.GONE);

                   class_id = -1;
                   section_id = -1;

               }else{
                   isChecked = false;
                   radioButton.setChecked(isChecked);

                   spnClass.setVisibility(View.VISIBLE);
                   spnSection.setVisibility(View.VISIBLE);


               }
           }
       });

        spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 0) {
                    section_name = sectionArray.get(i).getKey();
                    section_id = sectionArray.get(i).getValue();
                                Toast.makeText(getApplicationContext(),section_name+"   "+section_id,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i >= 0) {

                    class_name = classArray.get(i).getKey();
                    class_id = classArray.get(i).getValue();
                                Toast.makeText(getApplicationContext(),class_name+"   "+class_id,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.update_date:

                datePickerDialog = new DatePickerDialog(ContentAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        update_date = helper.selectDate(dayOfMonth, month, year);
                        txtUpdatedate.setText(update_date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

                break;

            case R.id.attach_file:

                helper.getFile(this);

                break;

            case R.id.btn_save_content:

                String title = et_title.getText().toString();
                String reason = et_reason.getText().toString();

                Toast.makeText(getApplicationContext(),title+"  "+reason,Toast.LENGTH_SHORT).show();

                break;

        }

    }
}
