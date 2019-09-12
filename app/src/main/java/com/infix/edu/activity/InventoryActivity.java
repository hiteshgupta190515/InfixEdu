package com.infix.edu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.adapter.InventoryAdapter;
import com.infix.edu.adapter.OptionAdapter;
import com.infix.edu.myconfig.MyConfig;
import com.infix.edu.R;
import com.infix.edu.model.Book;
import com.infix.edu.model.Inventory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity implements OptionAdapter.ClickListener {

    private ArrayList<Inventory> inventories = new ArrayList<>();
    private ArrayList<Book> tmp_books = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewOption;
    private InventoryAdapter adapter;
    private AutoCompleteTextView completeTextView;
    private ProgressBar progressBar;
    private StaggeredGridLayoutManager gridLayoutManager;
    private String[] names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        completeTextView = findViewById(R.id.actInventorySearch);

        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.inventoryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewOption = findViewById(R.id.inventoryRecyclerOptions);
        recyclerViewOption.setHasFixedSize(true);
        gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewOption.setLayoutManager(gridLayoutManager);

        names = getResources().getStringArray(R.array.inventory_functions_name);
        OptionAdapter optionAdapter = new OptionAdapter(names,InventoryActivity.this);
        optionAdapter.setClickListener(InventoryActivity.this);
        recyclerViewOption.setAdapter(optionAdapter);

        getAlInventories();

    }


    void getAlInventories(){

        inventories.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.INVENTORY_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("items");



                        for(int i = 0 ; i < array.length() ; i++){

                            String name = array.getJSONObject(i).getString("item_name");
                            int category_name = array.getJSONObject(i).getInt("category_name");
                            String des = array.getJSONObject(i).getString("description");
                            int total_in_stock = array.getJSONObject(i).getInt("total_in_stock");
                            int id = array.getJSONObject(i).getInt("id");
                            int school_id = array.getJSONObject(i).getInt("school_id");


                            inventories.add(new Inventory(id,school_id,MyConfig.getInventory(category_name),total_in_stock,des,name));

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(inventories.size() > 0){

                    adapter = new InventoryAdapter(inventories,getApplicationContext());
                    //adapter.setClickListener(LibraryActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
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

    @Override
    public void itemClicked(String s, int position, View view) {

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();

    }
}
