package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.adapter.FeeAdapter;
import com.infix.edu.model.Fee;
import com.infix.edu.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeesActivity extends AppCompatActivity {

    private ArrayList<Fee> fees = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeeAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private int totalAmount,totalFine,totalDiscount,totalPaid,totalBalance;
    private TextView txamount,txfine,txdiscount,txpaid,txbalance;
    private Toolbar toolbar;
    private TextView txtToolbarText;
    private String symbol;
    private String profile_image_url;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        txamount = findViewById(R.id.amount);
        txdiscount = findViewById(R.id.discount);
        txbalance = findViewById(R.id.balance);
        txfine = findViewById(R.id.fine);
        txpaid = findViewById(R.id.paid);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);
        profile = findViewById(R.id.profile);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);

        //getting id role_id url based on parents and child
        if(getIntent().getIntExtra("id",0) != 0){
            id = getIntent().getIntExtra("id",0);
        }else {
            id = sharedPreferences.getInt("id",0);
        }

        profile_image_url = sharedPreferences.getString("profile_image",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Fees");

        recyclerView = findViewById(R.id.feeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllFees(id);
        MyConfig.getProfileImage(profile_image_url,profile,FeesActivity.this);


    }


    void getAllFees(int id){

        fees.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getFeesUrl(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        totalAmount = 0;
                        totalDiscount = 0;
                        totalFine = 0;
                        totalPaid = 0;
                        totalBalance = 0;

                        JSONArray array = response.getJSONObject("data").getJSONArray("fees");
                        JSONObject currency = response.getJSONObject("data").getJSONObject("currency_symbol");
                        symbol = currency.getString("currency_symbol");


                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("fees_name");
                            String due_date = array.getJSONObject(i).getString("due_date");
                            int amount = array.getJSONObject(i).getInt("amount");
                            int paid = array.getJSONObject(i).getInt("paid");
                            int fine = array.getJSONObject(i).getInt("fine");
                            int discount = array.getJSONObject(i).getInt("discount_amount");
                            int balance = array.getJSONObject(i).getInt("balance");

                            totalBalance = totalBalance + balance;
                            totalPaid = totalPaid + paid;
                            totalFine = totalFine + fine;
                            totalAmount = totalAmount + amount;
                            totalDiscount = totalDiscount + discount;

                            Fee fee = new Fee(title,due_date,"paid",amount,paid,discount,fine,balance,symbol);
                            fees.add(fee);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(fees.size() > 0){

                    txamount.setText(symbol+totalAmount);
                    txbalance.setText(symbol+totalBalance);
                    txdiscount.setText(symbol+totalDiscount);
                    txfine.setText(symbol+totalFine);
                    txpaid.setText(symbol+totalPaid);

                    adapter = new FeeAdapter(fees,FeesActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

}
