package com.infix.studentmanagement.activity;

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
import com.infix.studentmanagement.R;
import com.infix.studentmanagement.adapter.BookIssuedAdapter;
import com.infix.studentmanagement.model.BookIssue;
import com.infix.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IssuedBookActivity extends AppCompatActivity {

    private ArrayList<BookIssue> books = new ArrayList<>();
    private RecyclerView recyclerView;
    private BookIssuedAdapter adapter;

    private Toolbar toolbar;
    private TextView txtToolbarText;

    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_book);

        sharedPreferences = getSharedPreferences("default", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);


        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Books Issued");


        //main recyclerview
        recyclerView = findViewById(R.id.issuedBookRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllIssuedBooks(id);

    }

    void getAllIssuedBooks(int id){

        books.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getStudentIssuedBooks(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONObject("data").getJSONArray("issueBooks");



                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("book_title");
                            String author = array.getJSONObject(i).getString("author_name");
                            String publisher = array.getJSONObject(i).getString("publisher_name");
                            String book_number = array.getJSONObject(i).getString("book_number");
                            String issue = array.getJSONObject(i).getString("given_date");
                            String givenDate = array.getJSONObject(i).getString("given_date");

                           BookIssue book = new BookIssue(issue,givenDate,book_number,"issued",title,author);

                            books.add(book);


                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(books.size() > 0){

                    adapter = new BookIssuedAdapter(books,getApplicationContext());
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
