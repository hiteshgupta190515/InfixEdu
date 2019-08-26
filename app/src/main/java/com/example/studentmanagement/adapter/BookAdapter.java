package com.example.studentmanagement.adapter;

import android.app.Activity;
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

    private static ArrayList<Book> books;
    private Context ctx;
    private static ClickListener clickListener;

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
        holder.subTitle.setText(books.get(position).getBookAuthor() +" | "+books.get(position).getBookPublisher());
        holder.subBookQuantity.setText(books.get(position).getQuantity()+"");
        holder.subName.setText(books.get(position).getSubject_name());
        holder.subBookPrice.setText(books.get(position).getBookPrice()+"");
        holder.subBookAddOn.setText(books.get(position).getBookReck());
        holder.subBookNo.setText(books.get(position).getBook_no());

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {

        void itemClicked(Book setterGetter, int position, View view);

    }


    public static class BookViewHolder extends RecyclerView.ViewHolder{

        TextView titleName;
        TextView subTitle;
        TextView subName;
        TextView subBookNo;
        TextView subBookQuantity;
        TextView subBookPrice;
        TextView subBookAddOn;


        public BookViewHolder(@NonNull View v) {
            super(v);

            titleName = v.findViewById(R.id.txtLibBookTitle);
            subTitle = v.findViewById(R.id.txtLibSubTitle);
            subName = v.findViewById(R.id.libSubject);
            subBookNo = v.findViewById(R.id.libBookNo);
            subBookQuantity = v.findViewById(R.id.libQuantity);
            subBookPrice = v.findViewById(R.id.libprice);
            subBookAddOn = v.findViewById(R.id.libAddOn);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.itemClicked(books.get(getPosition()), getPosition(),view);
                    }
                }
            });

        }
    }

}
