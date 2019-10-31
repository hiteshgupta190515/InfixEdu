package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infix.edu.R;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFeesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    private EditText et_name;
    private EditText et_description;
    private Button btn_save;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fees);


        et_name = findViewById(R.id.et_group_name);
        et_description = findViewById(R.id.et_description);
        btn_save = findViewById(R.id.btn_save_fees);
        helper = new Helper();

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add fees group");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,AddFeesActivity.this);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_name.getText().toString();
                String description = et_description.getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(description)){

                    helper.sent_fees_data(name,description,AddFeesActivity.this);

                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
