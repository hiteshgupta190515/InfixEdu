package com.infix.edu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;
import com.infix.edu.R;
import com.infix.edu.adapter.AdminAdapter;
import com.infix.edu.adapter.HomeAdapterHome;
import com.infix.edu.adapter.TeacherHomeAdapter;
import com.infix.edu.myconfig.Helper;
import com.infix.edu.myconfig.MyConfig;
import com.google.android.material.textfield.TextInputEditText;
import com.infix.edu.service.SensorService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity{

    private String[] names;
    private RecyclerView recycler;
    private GridLayoutManager gridLayoutManager;
    private int role_id;
    private int id;
    private int iId;
    private String email;
    private String password;
    private String name;
    private CircleImageView profile;
    private String url;
    private String profile_image_tag;

    private Toolbar toolbar;
    private TextView txtToolbarText;
    private AlertDialog.Builder alertBuilder;
    private AlertDialog dialog;
    private SharedPreferences sharedPreferences;
    private Helper helper;
    TextView txtProfile;
    TextView txtChangePassword;
    TextView txtLogout;
    Intent intent;
    SensorService sensorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recycler = findViewById(R.id.home_recycler);
        profile = findViewById(R.id.profile);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        helper = new Helper();

        sensorService = new SensorService(this);
        intent = new Intent(this, sensorService.getClass());
        if (!isMyServiceRunning(sensorService.getClass())) {
            startService(intent);
        }

        gridLayoutManager = new GridLayoutManager(HomeActivity.this,4);
        recycler.setLayoutManager(gridLayoutManager);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
        email = sharedPreferences.getString("email", null);
        password = sharedPreferences.getString("password",null);
        name = sharedPreferences.getString("name", null);

        //check domain exists or not if not logout
        isEnabled();

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("rule_id",0) == 2){
            iId = getIntent().getIntExtra("id",0);
            role_id = getIntent().getIntExtra("rule_id",0);
            url = MyConfig.getChildren(iId);
            profile_image_tag = "student_photo";

        }else{
            role_id = sharedPreferences.getInt("role", 0);
            id = sharedPreferences.getInt("id", 0);
            url = MyConfig.getLoginUrl(email, password);

            //getting device token from this line
            helper.setToken(id,FirebaseInstanceId.getInstance().getToken(),this);

            //getting this content by role_id and show profile image by this tag
            if(role_id == 2)
                profile_image_tag = "student_photo";
            if(role_id == 3)
                profile_image_tag = "fathers_photo";
            if(role_id == 1)
                profile_image_tag = "staff_photo";
        }


        getFunctionality(role_id);
        update_profile_image(url,profile_image_tag);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getIntExtra("rule_id",0) != 2){

                    alertBuilder = new AlertDialog.Builder(HomeActivity.this);
                    View mView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.user_image_click_layout, null);

                    txtProfile = mView.findViewById(R.id.profile);
                    txtChangePassword = mView.findViewById(R.id.change_password);
                    txtLogout = mView.findViewById(R.id.logout);

                    //hide profile view if parents open student profile
                    if(role_id != 2)txtProfile.setVisibility(View.GONE);

                    txtProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                            dialog.dismiss();
                        }
                    });
                    txtChangePassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                            builder.setMessage("change password");
                            View v = LayoutInflater.from(HomeActivity.this).inflate(R.layout.change_password_layout,null);

                            final TextInputEditText etCurrentPass = v.findViewById(R.id.etCurrentPassword);
                            final TextInputEditText etNewPass = v.findViewById(R.id.etNewPassword);
                            final TextInputEditText etConfirmPass = v.findViewById(R.id.etNConfirmPassword);
                            Button btnEnter = v.findViewById(R.id.btn_change_password_enter);

                            builder.setView(v);
                            final AlertDialog d = builder.create();

                            btnEnter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    String current = etCurrentPass.getText().toString();
                                    String newP = etNewPass.getText().toString();
                                    String confirm = etConfirmPass.getText().toString();


                                    if(current != "" && newP != ""){

                                        if(newP.length() >= 6){

                                            if(newP.equals(confirm)){
                                                changePassword(current,newP);
                                                d.dismiss();
                                                dialog.dismiss();


                                            }else{
                                                Toast.makeText(getApplicationContext(),"new password and confirm password does not match",Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            Toast.makeText(getApplicationContext(),"Password is too short",Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                }
                            });


                            d.show();


                        }
                    });
                    txtLogout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            AlertDialog.Builder bld = new AlertDialog.Builder(HomeActivity.this);

                            bld.setTitle("Logout").setMessage("Do you want to logout?").create();

                            bld.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    SharedPreferences pref = getSharedPreferences("default", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.commit();

                                    editor.putBoolean("isLoged", false);

                                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }).setNegativeButton("No",null);

                            AlertDialog dg = bld.create();
                            dg.show();

                        }
                    });



                    alertBuilder.setView(mView);
                    dialog = alertBuilder.create();

                    Rect displayRectangle = new Rect();
                    Window window = dialog.getWindow();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);


                    dialog.getWindow().setGravity(Gravity.RIGHT|Gravity.TOP);
                    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                    layoutParams.y = 100; // top margin
                    dialog.getWindow().setAttributes(layoutParams);
                    dialog.show();


                }


            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }

    private void showPasswordChangedSuccess(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(HomeActivity.this,R.style.DialogTheme);
        View mView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.pssword_change_success, null);


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

    private void changePassword(String current, final String newP) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.changePassword(id,current,newP), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                try {
                    if(response.getBoolean("success")){

                        editor.putString("password", newP);
                        editor.commit();
                        showPasswordChangedSuccess();

                    }else{
                        Toast.makeText(getApplicationContext(),"password change unsuccessful!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(request);

    }


    private void getFunctionality(int role) {

        ArrayList<Integer> images = new ArrayList<>();
        images.clear();

        switch (role){
                case 1:
                    names = getResources().getStringArray(R.array.admins_functions_name);
                break;
                case 2:
                    names = getResources().getStringArray(R.array.students_functions_name);
                break;
                case 3:
                    names = getResources().getStringArray(R.array.parents_functions_name);
                break;
                case 4:
                    names = getResources().getStringArray(R.array.teachers_functions_name);
                break;
                default:
                    names = getResources().getStringArray(R.array.default_functions_name);
                    break;

        }

        for(String n : names){

            images.add(imageSearch(n.toLowerCase().replace(" ","")));
        }

        if(role_id == 4){
            TeacherHomeAdapter teacherHomeAdapter = new TeacherHomeAdapter(this,names,images,this);
            recycler.setAdapter(teacherHomeAdapter);
        }else if(role_id == 1){
            AdminAdapter adminAdapter = new AdminAdapter(this,names,images,this);
            recycler.setAdapter(adminAdapter);
        }else{
            HomeAdapterHome homeAdapterHome = new HomeAdapterHome(this,names,images,iId,this);
            recycler.setAdapter(homeAdapterHome);
        }

    }
    private int imageSearch(String text) {

        int img = 0;
        try{

            img = getResources().getIdentifier(text, "mipmap", getPackageName());

            if(img == 0){
                img = getResources().getIdentifier("inventory", "mipmap", getPackageName());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
  return img;
    }



    @Override
    public void onBackPressed() {

        finish();

    }

    private void update_profile_image(String url, final String s){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getBoolean("success")){

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        JSONObject detailsObj;

                        detailsObj = response.getJSONObject("data").optJSONObject("userDetails");
                        String image = detailsObj.getString(s);

                        Glide.with(HomeActivity.this)
                                .load(MyConfig.ROOT_URL+image)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .fitCenter()
                                .into(profile);

                        editor.putString("profile_image", MyConfig.ROOT_URL+image);
                        editor.commit();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Loading error", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put("email", email);
                params.put("password", password);

                //returning parameter
                return params;
            }
        };
        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void isEnabled(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.isEnabled(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(!response.getBoolean("success")){

                        SharedPreferences pref = getSharedPreferences("default", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();

                        Toast.makeText(HomeActivity.this, "please contact with support team!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }

}
