package com.infix.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.infix.edu.R;
import com.infix.edu.model.Book;

import java.util.ArrayList;

public class AutoCompleteBookAdapter extends ArrayAdapter<Book> {


    private ArrayList<Book> items;
    private ArrayList<Book> itemsAll;
    private ArrayList<Book> suggestions;
    private int viewResourceId;

    public AutoCompleteBookAdapter(Context context, int viewResourceId, ArrayList<Book> items) {
        super(context, viewResourceId,items);
        this.items = items;
        this.itemsAll = (ArrayList<Book>) items.clone();
        this.suggestions = new ArrayList<Book>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Book product = items.get(position);
        if (product != null) {
            TextView title =  v.findViewById(R.id.book_title);
            TextView author =  v.findViewById(R.id.book_author);
            if (title != null && author != null) {
                title.setText(product.getBookTitle());
                author.setText(product.getBookAuthor());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            String str = ((Book) (resultValue)).getBookTitle();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Book product : itemsAll) {
                    if (product.getBookTitle().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<Book> filteredList = (ArrayList<Book>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Book c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}
