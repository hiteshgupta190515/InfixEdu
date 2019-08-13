package com.example.studentmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanagement.R;
import com.example.studentmanagement.activity.BookDetailsActivity;
import com.example.studentmanagement.activity.StudentListActivity;
import com.example.studentmanagement.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private ArrayList<Book> books;
    private Context ctx;

    public BookAdapter(ArrayList<Book> books, Context ctx) {
        this.books = books;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item,parent,false);

        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        holder.titleName.setText(books.get(position).getBookTitle());
        holder.author.setText(books.get(position).getBookAuthor());

        holder.relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ctx.startActivity(new Intent(ctx, BookDetailsActivity.class));
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx);
//                View mView = LayoutInflater.from(ctx).inflate(R.layout.activity_book_details, null);
//
//                TextView txtTitle = mView.findViewById(R.id.book_title);
//                TextView txtAuthor = mView.findViewById(R.id.book_author);
//                TextView txtPublisher = mView.findViewById(R.id.txtPublisher);
//
//
//                alertBuilder.setView(mView);
//                AlertDialog dialog = alertBuilder.create();
//                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        TextView titleName;
        TextView author;
        RelativeLayout relative;


        public BookViewHolder(@NonNull View v) {
            super(v);

            titleName = v.findViewById(R.id.book_title);
            author = v.findViewById(R.id.book_author);
            relative = v.findViewById(R.id.reletive);

        }
    }

}
