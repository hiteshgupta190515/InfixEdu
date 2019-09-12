package com.infix.edu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.infix.edu.R;
import com.infix.edu.model.ChooseExam;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    private ArrayList<ChooseExam> chooseExams;
    private int viewResourceId;
    private Context ctx;

    public SpinnerAdapter(ArrayList<ChooseExam> chooseExams, int viewResourceId, Context ctx) {
        this.chooseExams = chooseExams;
        this.viewResourceId = viewResourceId;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return chooseExams.size();
    }

    @Override
    public Object getItem(int i) {
        return chooseExams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v =  View.inflate(ctx, viewResourceId, null);
        TextView textView =v.findViewById(R.id.spn_text);
        textView.setText(chooseExams.get(i).getExamName());
        return textView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(ctx, R.layout.spinner_dropdown, null);
        final TextView textView = view.findViewById(R.id.dropdown);
        textView.setText(chooseExams.get(position).getExamName());

        return view;
    }
}
