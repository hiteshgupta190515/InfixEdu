 package com.example.studentmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studentmanagement.R;
import com.example.studentmanagement.adapter.BookAdapter;
import com.example.studentmanagement.adapter.StudentListAdapter;
import com.example.studentmanagement.model.Book;
import com.example.studentmanagement.myconfig.MyConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

 public class LibraryActivity extends AppCompatActivity {

    private ArrayList<Book> books = new ArrayList<>();
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        recyclerView = findViewById(R.id.libraryRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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



//                            String title = array.getJSONObject(0).getString("book_title");
//                            String author = array.getJSONObject(0).getString("author_name");
//                            String publisher = array.getJSONObject(0).getString("publisher_name");
//
//
//
//
//                        Toast.makeText(getApplicationContext(),title +" "+author+" "+publisher, Toast.LENGTH_SHORT).show();



                        for(int i = 0 ; i < array.length() ; i++){

                            String title = array.getJSONObject(i).getString("book_title");
                            String author = array.getJSONObject(i).getString("author_name");
                            String publisher = array.getJSONObject(i).getString("publisher_name");
                            String rack_number = array.getJSONObject(i).getString("rack_number");
                            String description = array.getJSONObject(i).getString("details");
                            double price = array.getJSONObject(i).getDouble("book_price");
                            String isbn_no = array.getJSONObject(i).getString("isbn_no");
                            int id = array.getJSONObject(i).getInt("id");
                            int cate_id = array.getJSONObject(i).getInt("book_category_id");
                            int quantity = array.getJSONObject(i).getInt("quantity");
                            int active_status = array.getJSONObject(i).getInt("active_status");

                            Book book = new Book(title,author,publisher,price,rack_number
                                    ,description,isbn_no,id,cate_id,quantity,active_status);

                            books.add(book);

                            Log.d("books",title);

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(books.size() > 0){

                    adapter = new BookAdapter(books,getApplicationContext());
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
