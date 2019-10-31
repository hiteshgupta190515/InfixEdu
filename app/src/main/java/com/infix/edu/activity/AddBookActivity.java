package com.infix.edu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spn_category,spn_subject;
    private ArrayList<SearchData> categories,subjects;
    private ArrayList<String> categorieStr,subjectStr;
    private int category,subject;
    private SharedPreferences sharedPreferences;
    private int id;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String profile_image_url;
    private CircleImageView profile;
    private EditText etTitle , etBookNo ,etIsbn,etPublisherName, etAuthorName,etReckNo,etQuality,etPrice,etDescription;
    private TextView txtdate;
    private Button btnSave;
    private int year, month, day;
    private DatePickerDialog datePickerDialog;
    private Helper helper = new Helper();
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        spn_category = findViewById(R.id.choose_category_spinner);
        spn_subject = findViewById(R.id.choose_subject_spinner);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        etTitle = findViewById(R.id.title);
        etBookNo = findViewById(R.id.book_no);
        etIsbn = findViewById(R.id.isbn_no);
        etPublisherName = findViewById(R.id.publisher_name);
        etAuthorName = findViewById(R.id.author_name);
        etReckNo = findViewById(R.id.reck_no);
        etQuality = findViewById(R.id.quantity);
        etPrice = findViewById(R.id.price);
        etDescription = findViewById(R.id.description);
        txtdate = findViewById(R.id.date);
        btnSave = findViewById(R.id.btnSaveBook);
        txtdate.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        categories = new ArrayList<>();
        subjects = new ArrayList<>();
        categories.clear();
        subjects.clear();
        categorieStr = new ArrayList<>();
        subjectStr = new ArrayList<>();
        categorieStr.clear();
        subjectStr.clear();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Book");
        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,AddBookActivity.this);

        categories = helper.getBookCategoryData(AddBookActivity.this);
        subjects = helper.subjectList(AddBookActivity.this);


        getCategoriesAndSubject();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.date:
                datePickerDialog = new DatePickerDialog(AddBookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = helper.selectDate(dayOfMonth, month, year);
                        txtdate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.btnSaveBook:
                send_data_to_server();
                break;


        }


    }

    void getCategoriesAndSubject(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i = 0 ; i < categories.size() ; i++){
                    categorieStr.add(categories.get(i).getKey());
                }

                for(int i = 0 ; i < subjects.size() ; i++){
                    subjectStr.add(subjects.get(i).getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddBookActivity.this, R.layout.spinner_row_layout, R.id.spn_text, categorieStr);
                spn_category.setAdapter(adapter);

                ArrayAdapter adapter1 = new ArrayAdapter(AddBookActivity.this, R.layout.spinner_row_layout, R.id.spn_text, subjectStr);
                spn_subject.setAdapter(adapter1);

                spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i >= 0) {
                            category = categories.get(i).getValue();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                spn_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (i >= 0) {
                            subject = subjects.get(i).getValue();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        },2000);

    }

    void send_data_to_server(){

        String title = etTitle.getText().toString();
        String book_no = etBookNo.getText().toString();
        String isbn = etIsbn.getText().toString();
        String pub_name = etPublisherName.getText().toString();
        String author_name = etAuthorName.getText().toString();
        String reck = etReckNo.getText().toString();
        String quantity = etQuality.getText().toString();
        String price = etPrice.getText().toString();
        String description = etDescription.getText().toString();

        if(!TextUtils.isEmpty(title))
        helper.send_book_data(this,title,String.valueOf(category),book_no,isbn,pub_name,author_name,String.valueOf(subject),reck,quantity,price,description,date,String.valueOf(id));
        else
            Toast.makeText(getApplicationContext(), "title is requied", Toast.LENGTH_SHORT).show();

    }


}
