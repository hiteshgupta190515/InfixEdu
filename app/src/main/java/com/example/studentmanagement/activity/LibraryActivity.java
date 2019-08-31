 package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.AutoCompleteBookAdapter;
import com.example.studentmanagement.adapter.BookAdapter;
import com.example.studentmanagement.adapter.OptionAdapter;
import com.example.studentmanagement.adapter.StudentListAdapter;
import com.example.studentmanagement.model.Book;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

 public class LibraryActivity extends AppCompatActivity implements BookAdapter.ClickListener{

    private ArrayList<Book> books = new ArrayList<>();
    private RecyclerView recyclerView;
    private BookAdapter adapter;

     private Toolbar toolbar;
     private TextView txtToolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        toolbar = findViewById(R.id.toolbar);
        txtToolbarText = findViewById(R.id.txtTitle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbarText.setText("Books List");


        //main recyclerview
        recyclerView = findViewById(R.id.libraryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        completeTextView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//
//                tmp_books.clear();
//
//                if(i2 == 0 ) {
//                    getAllBooks();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        getAllBooks();

    }

    void getAllBooks(){

        books.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.BOOK_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getBoolean("success")){

                        JSONArray array = response.getJSONArray("data");

                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("book_title");
                            String author = array.getJSONObject(i).getString("author_name");
                            String publisher = array.getJSONObject(i).getString("publisher_name");
                            String rack_number = array.getJSONObject(i).getString("rack_number");
                            String description = array.getJSONObject(i).getString("details");
                            int price = array.getJSONObject(i).getInt("book_price");
                            String isbn_no = array.getJSONObject(i).getString("isbn_no");
                            int id = array.getJSONObject(i).getInt("id");
                            int cate_id = array.getJSONObject(i).getInt("book_category_id");
                            int quantity = array.getJSONObject(i).getInt("quantity");
                            int active_status = array.getJSONObject(i).getInt("active_status");
                            String book_number = array.getJSONObject(i).getString("book_number");
                            String subject = array.getJSONObject(i).getString("subject_name");

                            Book book = new Book(title,author,publisher,price,rack_number
                                    ,description,isbn_no,id,cate_id,quantity,active_status,subject,book_number);

                            books.add(book);


                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(books.size() > 0){

                    adapter = new BookAdapter(books,getApplicationContext());
                    adapter.setClickListener(LibraryActivity.this);
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


     @Override
     public void itemClicked(Book setterGetter, int position, View view) {

                Intent intent = new Intent(this,BookDetailsActivity.class);
                startActivity(intent);
                finish();

     }

 }
