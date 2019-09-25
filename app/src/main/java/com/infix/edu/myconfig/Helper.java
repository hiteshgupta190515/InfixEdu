package com.infix.edu.myconfig;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.activity.AddHomeWorkActivity;
import com.infix.edu.model.SearchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Helper {

    private int value;
    private ArrayList<SearchData> classData = new ArrayList<>();
    private ArrayList<SearchData> sectionData = new ArrayList<>();
    private ArrayList<String> classes = new ArrayList<>();
    private ArrayList<String> sections = new ArrayList<>();

    public int getClassAndSectionName(final Spinner spnClass, final Spinner spnSection, final Context ctx) {

        classData.clear();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getClassAndSection(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONObject main = response.getJSONObject("data");
                        JSONArray classNameArray = main.getJSONArray("classes");
                        JSONArray sectionNameArray = main.getJSONArray("sections");

                        classes.add("select classes");
                        sections.add("select section");

                        for (int i = 0; i < classNameArray.length(); i++) {

                            String className = classNameArray.getJSONObject(i).getString("class_name");
                            classes.add(className);
                            int class_id = classNameArray.getJSONObject(i).getInt("id");
                            classData.add(new SearchData(className, class_id));

                        }

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("section_name");
                            sections.add(sectionName);
                            int section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (classData.size() > 0) {

                    ArrayAdapter adapter1 = new ArrayAdapter(ctx, R.layout.spinner_row_layout, R.id.spn_text, classes);
                    spnClass.setAdapter(adapter1);

                    ArrayAdapter adapter2 = new ArrayAdapter(ctx, R.layout.spinner_row_layout, R.id.spn_text, sections);
                    spnSection.setAdapter(adapter2);

                    spnSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i > 0) {
                                int section_id = classData.get(i - 1).getValue();
//                                Toast.makeText(getApplicationContext(),sectionData.get(i-1).getKey()+"   "+sectionData.get(i-1).getValue(),Toast.LENGTH_SHORT).show();
                                value = section_id;
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (i > 0) {
                                int class_id = classData.get(i - 1).getValue();
//                                Toast.makeText(getApplicationContext(),classData.get(i-1).getKey()+"   "+classData.get(i-1).getValue(),Toast.LENGTH_SHORT).show();

                                value = class_id;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(ctx);
        req.add(request);

        return value;

    }



}
