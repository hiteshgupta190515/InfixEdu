package com.infix.studentmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infix.studentmanagement.R;
import com.infix.studentmanagement.model.BookIssue;

import java.util.ArrayList;

public class BookIssuedAdapter extends RecyclerView.Adapter<BookIssuedAdapter.IssuedViewHolder>{

    private ArrayList<BookIssue> bookIssues;
    private Context ctx;

    public BookIssuedAdapter(ArrayList<BookIssue> bookIssues, Context ctx) {
        this.bookIssues = bookIssues;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public IssuedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_issued_row_layout,parent,false);

        return new IssuedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IssuedViewHolder holder, int position) {

        holder.issueDate.setText(bookIssues.get(position).getIssue_date());
        holder.issueReturn.setText(bookIssues.get(position).getReturn_date());
        holder.issueBookNo.setText(bookIssues.get(position).getBook_no());
        holder.issueStatus.setText(bookIssues.get(position).getStatus());
        holder.issueTitleName.setText(bookIssues.get(position).getTitle());
        holder.issueSubTitle.setText(bookIssues.get(position).getSubTitle());


    }

    @Override
    public int getItemCount() {
        return bookIssues.size();
    }

    public static class IssuedViewHolder extends RecyclerView.ViewHolder{

        TextView issueTitleName;
        TextView issueSubTitle;
        TextView issueDate;
        TextView issueReturn;
        TextView issueBookNo;
        TextView issueStatus;

        public IssuedViewHolder(@NonNull View v) {
            super(v);

            issueTitleName = v.findViewById(R.id.txtLibIssueBookTitle);
            issueSubTitle = v.findViewById(R.id.txtLibIssueSubTitle);
            issueBookNo = v.findViewById(R.id.issuedBookNo);
            issueStatus = v.findViewById(R.id.issueBookStatus);
            issueDate = v.findViewById(R.id.issueDate);
            issueReturn = v.findViewById(R.id.returnDate);

        }
    }

}
