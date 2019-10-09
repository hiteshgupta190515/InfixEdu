package com.infix.edu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddDormitoryRoomActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    private EditText et_room_no,et_no_of_bed,et_cost_bed,et_description;
    private Spinner spn_dormitory,spn_room_type;
    private Button btn_save;
    private ArrayList<SearchData> roomTypes = new ArrayList<>();
    private ArrayList<String> roomTypeData = new ArrayList<>();
    private ArrayList<String> dormitoriesNameData = new ArrayList<>();
    private ArrayList<SearchData> dormitories = new ArrayList<>();
    private Helper helper;
    private int room_id;
    private int dormitory_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dormitory_room);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        helper = new Helper();
        roomTypes = helper.getRoomTypeData(this);
        dormitories = helper.getDormitoryListeData(this);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        et_room_no = findViewById(R.id.et_room_no);
        et_no_of_bed = findViewById(R.id.et_no_of_bed);
        et_cost_bed = findViewById(R.id.et_cost_per_bed);
        et_description = findViewById(R.id.et_description);
        spn_dormitory = findViewById(R.id.choose_dormitory_spinner);
        spn_room_type = findViewById(R.id.choose_dormitory_type_spinner);
        btn_save = findViewById(R.id.btn_save);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Room");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,AddDormitoryRoomActivity.this);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String room_no = et_room_no.getText().toString();
                String no_of_bed = et_no_of_bed.getText().toString();
                String cost = et_cost_bed.getText().toString();
                String description = et_description.getText().toString();

                if(!TextUtils.isEmpty(room_no) && !TextUtils.isEmpty(no_of_bed) &&
                        !TextUtils.isEmpty(cost) && !TextUtils.isEmpty(description)){

                    send_room_data(room_no,no_of_bed,cost,description,dormitory_id,room_id);

                }

            }
        });

        showSpinnerData();

    }


    private void showSpinnerData(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                for(SearchData s: roomTypes){
                    roomTypeData.add(s.getKey());
                }
                for(SearchData s: dormitories){
                    dormitoriesNameData.add(s.getKey());
                }

                ArrayAdapter adapter = new ArrayAdapter(AddDormitoryRoomActivity.this, R.layout.spinner_row_layout, R.id.spn_text, roomTypeData);
                spn_room_type.setAdapter(adapter);

                ArrayAdapter adapter1 = new ArrayAdapter(AddDormitoryRoomActivity.this, R.layout.spinner_row_layout, R.id.spn_text, dormitoriesNameData);
                spn_dormitory.setAdapter(adapter1);

                spn_room_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            room_id = roomTypes.get(i).getValue();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                spn_dormitory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            dormitory_id = dormitories.get(i).getValue();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }
        }, 1000);
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

    void send_room_data(final String room_no, final String no_of_bed, final String cost, final String description, final int dor_id, final int type_id){

//        JSONObject post = new JSONObject();
//
//        try {
//            post.put("room_number",room_no);
//            post.put("dormitory", String.valueOf(dor_id));
//            post.put("room_type", String.valueOf(type_id));
//            post.put("number_of_bed",no_of_bed);
//            post.put("cost_per_bed",cost);
//            post.put("description",description);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyConfig.ADMIN_DORMITORY_ROOM_LIST, post, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    if(response.getBoolean("success")){
//
//                        Toast.makeText(AddDormitoryRoomActivity.this,"data save successfully",Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(AddDormitoryRoomActivity.this,error.getMessage()+".",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConfig.ADMIN_DORMITORY_ROOM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("success"))

                        showSuccess();

                    else
                        Toast.makeText(AddDormitoryRoomActivity.this,"data does not save",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddDormitoryRoomActivity.this,"server does not response",Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> post = new HashMap<>();
                post.put("room_number",room_no);
                post.put("dormitory", String.valueOf(dor_id));
                post.put("room_type", String.valueOf(type_id));
                post.put("number_of_bed",no_of_bed);
                post.put("cost_per_bed",cost);
                post.put("description",description);
                post.put("Content-Type","application/x-www-form-urlencoded");
                return post;


            }
        };

        requestQueue.add(stringRequest);

    }

    private void showSuccess(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },5000);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddDormitoryRoomActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(AddDormitoryRoomActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Room add successfully!");

        alertBuilder.setView(mView);
        AlertDialog dialog = alertBuilder.create();

        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        mView.setMinimumWidth((int)(displayRectangle.width()));
        mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

}
