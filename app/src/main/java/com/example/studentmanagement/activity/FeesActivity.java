package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.FeeAdapter;
import com.example.studentmanagement.adapter.VisitorAdapter;
import com.example.studentmanagement.model.Fee;
import com.example.studentmanagement.model.Visitor;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeesActivity extends AppCompatActivity {

    private ArrayList<Fee> fees = new ArrayList<>();
    private RecyclerView recyclerView;
    private FeeAdapter adapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private int totalAmount,totalFine,totalDiscount,totalPaid,totalBalance;
    private TextView txamount,txfine,txdiscount,txpaid,txbalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        txamount = findViewById(R.id.amount);
        txdiscount = findViewById(R.id.discount);
        txbalance = findViewById(R.id.balance);
        txfine = findViewById(R.id.fine);
        txpaid = findViewById(R.id.paid);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);

        recyclerView = findViewById(R.id.feeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllFees(id);


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

                            Fee fee = new Fee(title,due_date,"paid",amount,paid,discount,fine,balance);
                            fees.add(fee);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(fees.size() > 0){

                    txamount.setText("$"+totalAmount);
                    txbalance.setText("$"+totalBalance);
                    txdiscount.setText("$"+totalDiscount);
                    txfine.setText("$"+totalFine);
                    txpaid.setText("$"+totalPaid);

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
