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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddRouteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private SharedPreferences sharedPreferences;
    private String profile_image_url;
    private CircleImageView profile;
    private EditText et_title;
    private EditText et_fare;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        et_title = findViewById(R.id.et_route_title);
        et_fare = findViewById(R.id.et_fare);
        btn_save = findViewById(R.id.btn_save);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Add Route");
        profile = findViewById(R.id.profile);
        profile_image_url = sharedPreferences.getString("profile_image",null);
        MyConfig.getProfileImage(profile_image_url,profile,AddRouteActivity.this);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = et_title.getText().toString();
                String fare = et_fare.getText().toString();

                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(fare)){
                    send_route_data(title,fare);
                }

            }
        });


    }

    void send_route_data(final String title, final String fare){

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Toast.makeText(getApplicationContext(),dormitory_name+" "+intake+" "+type+" "+description+" "+address,Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyConfig.addRoute(title,fare), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("success"))

                        showSuccess();

                    else
                        Toast.makeText(AddRouteActivity.this,"data does not save",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddRouteActivity.this,"server does not response",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);

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

    private void showSuccess(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddRouteActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(AddRouteActivity.this).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText("Route add successfully!");

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
