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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.model.SearchData;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    private EditText et_vehicle_no;
    private EditText et_vehicle_model;
    private EditText et_vehicle_note;
    private EditText et_vehicle_made;
    private Spinner vehicle_driver_spiner;
    private ArrayList<SearchData> driverlist = new ArrayList<>();
    private ArrayList<String> driverName = new ArrayList<>();
    private Helper helper;
    private int driver_id;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        helper = new Helper();
        driverlist = helper.getAllDriver(this);

        btn_save = findViewById(R.id.btn_save);
        et_vehicle_no = findViewById(R.id.et_vehicle_no);
        et_vehicle_model =findViewById(R.id.et_model);
        et_vehicle_note = findViewById(R.id.et_note);
        et_vehicle_made = findViewById(R.id.et_made_year);
        vehicle_driver_spiner  = findViewById(R.id.choose_driver_spinner);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Vehicle");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,AddVehicleActivity.this);

        showSpinnerData();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String vehicle_no = et_vehicle_no.getText().toString();
                String vehicle_model = et_vehicle_model.getText().toString();
                String vehicle_note = et_vehicle_note.getText().toString();
                String vehicle_made = et_vehicle_made.getText().toString();

                if(!TextUtils.isEmpty(vehicle_no) && !TextUtils.isEmpty(vehicle_model) && !TextUtils.isEmpty(vehicle_note) && !TextUtils.isEmpty(vehicle_made)){

                    send_route_data(vehicle_no,vehicle_model,String.valueOf(driver_id),vehicle_made,vehicle_note);

                }

            }
        });


    }

    private void showSpinnerData(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(SearchData s: driverlist){
                    driverName.add(s.getKey());
                }

                ArrayAdapter adapter1 = new ArrayAdapter(AddVehicleActivity.this, R.layout.spinner_row_layout, R.id.spn_text, driverName);
                vehicle_driver_spiner.setAdapter(adapter1);

                vehicle_driver_spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        driver_id = driverlist.get(i).getValue();


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

    void send_route_data(final String vehicleNo, final String vehicleMOdel,String driverId,String madeYear,String note){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConfig.addVehicle(vehicleNo,vehicleMOdel,driverId,note,madeYear), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("success"))

                        showSuccess();

                    else
                        Toast.makeText(AddVehicleActivity.this,"data does not save",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddVehicleActivity.this,"server does not response",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

    }

    private void showSuccess(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddVehicleActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(AddVehicleActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Vehicle add successfully!");

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
