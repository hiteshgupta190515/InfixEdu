package com.infix.edu.myconfig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.activity.AddHomeWorkActivity;
import com.infix.edu.adapter.StudentListAdapter;
import com.infix.edu.model.SearchData;
import com.nbsp.materialfilepicker.MaterialFilePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Helper {

    private boolean isSuccess;

    public ArrayList<SearchData> getClassData(final Context ctx) {

        final ArrayList<SearchData> classData = new ArrayList<>();
        classData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getClassAndSection(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONObject main = response.getJSONObject("data");
                        JSONArray classNameArray = main.getJSONArray("classes");


                        for (int i = 0; i < classNameArray.length(); i++) {

                            String className = classNameArray.getJSONObject(i).getString("class_name");
                            int class_id = classNameArray.getJSONObject(i).getInt("id");
                            classData.add(new SearchData(className, class_id));

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return classData;

    }


    public ArrayList<SearchData> getSectionData(final Context ctx) {

        final ArrayList<SearchData> sectionData = new ArrayList<>();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getClassAndSection(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONObject main = response.getJSONObject("data");
                        JSONArray sectionNameArray = main.getJSONArray("sections");

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("section_name");
                            int section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


//                        Toast.makeText(getApplicationContext(),title +" "+array.length(), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return sectionData;

    }

    public ArrayList<SearchData> getRoomTypeData(final Context ctx) {

        final ArrayList<SearchData> sectionData = new ArrayList<>();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.ADMIN_DORMITORY_ROOM_TYPE_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONObject main = response.getJSONObject("data");
                        JSONArray sectionNameArray = main.getJSONArray("room_type_lists");

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("type");
                            int section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return sectionData;

    }

    public ArrayList<SearchData> getDormitoryListeData(final Context ctx) {

        final ArrayList<SearchData> sectionData = new ArrayList<>();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.ADMIN_DORMITORY_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONObject main = response.getJSONObject("data");
                        JSONArray sectionNameArray = main.getJSONArray("dormitory_lists");

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("dormitory_name");
                            int section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return sectionData;

    }

    public ArrayList<SearchData> getAllDriver(final Context ctx) {

        final ArrayList<SearchData> sectionData = new ArrayList<>();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.DRIVER_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        JSONArray sectionNameArray = response.getJSONArray("data");

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("full_name");
                            int section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return sectionData;

    }

    public boolean setLeaveStatus(int id, String s, final Context ctx) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.setLeaveStatus(id,s), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        isSuccess = true;

                        Toast.makeText(ctx,"successful",Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(ctx,"not successful",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return isSuccess;

    }

    public void setAttendanceData(String id,String atten, final String date, final Context ctx) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.attendance_data_send(id,atten,date), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        Toast.makeText(ctx,"successful",Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(ctx,"not successful",Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, "error", Toast.LENGTH_SHORT).show();
            }

        });

        RequestQueue req = Volley.newRequestQueue(ctx);
        req.add(request);
    }

    public boolean setToken(int id, String token, final Context ctx) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.setToken(id,token), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        isSuccess = true;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue req = Volley.newRequestQueue(ctx);
        req.add(request);

        return isSuccess;

    }

    public boolean sentNotification(String title,String body, String token,final Context ctx) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.sentNotification(title,body,token), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getInt("success") == 1) {

                        isSuccess = true;


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return isSuccess;
    }

    public void sentNotificationForAll(int id, final Context ctx) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.sentNotificationForAll(id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        JSONArray array = response.getJSONArray("data");

                        for (int i = 0 ; i < array.length() ; i++){

                            String token = array.getJSONObject(i).getString("notificationToken");

                            sentNotification("Content upload","A new content uploaded please check",token,ctx);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue req = Volley.newRequestQueue(ctx);
        req.add(request);

    }

    public String selectDate(int day, int month, int year) {

        String monthYear;
        String dayMonth;

        if (month + 1 < 10) {
            monthYear = "0" + String.valueOf(month + 1);
        } else {
            monthYear = String.valueOf(month + 1);
        }
        if (day < 10) {
            dayMonth = "0" + String.valueOf(day);
        } else {
            dayMonth = String.valueOf(day);
        }

        return year + "-" + monthYear + "-" + dayMonth;

    }

    public void getFile(Activity activity) {
//        Intent intent = new Intent();
//        intent.setType("*/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        activity.startActivityForResult(Intent.createChooser(intent, "Select PDF"), 1);

        new MaterialFilePicker()
                .withActivity(activity)
                .withRequestCode(1)
                .start();

    }

    public String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public String getPathFromUri(Uri uri, Activity activity) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public ArrayList<SearchData> getAllRoleTypes(final Context ctx) {

        final ArrayList<SearchData> sectionData = new ArrayList<>();
        sectionData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.STAFF_ROLE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        JSONArray sectionNameArray = response.getJSONArray("data");

                        for (int i = 0; i < sectionNameArray.length(); i++) {

                            String sectionName = sectionNameArray.getJSONObject(i).getString("name");
                            int section_id = sectionNameArray.getJSONObject(i).getInt("id");
                            sectionData.add(new SearchData(sectionName, section_id));

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return sectionData;

    }

    public ArrayList<SearchData> getBookCategoryData(final Context ctx) {

        final ArrayList<SearchData> bookData = new ArrayList<>();
        bookData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.BOOK_CATEGORY, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONArray classNameArray = response.getJSONArray("data");


                        for (int i = 0; i < classNameArray.length(); i++) {

                            String className = classNameArray.getJSONObject(i).getString("category_name");
                            int class_id = classNameArray.getJSONObject(i).getInt("id");
                            bookData.add(new SearchData(className, class_id));

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return bookData;

    }

    public ArrayList<SearchData> subjectList(final Context ctx) {

        final ArrayList<SearchData> subjectData = new ArrayList<>();
        subjectData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.SUBJECT_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        JSONArray classNameArray = response.getJSONArray("data");


                        for (int i = 0; i < classNameArray.length(); i++) {

                            String className = classNameArray.getJSONObject(i).getString("subject_name");
                            int class_id = classNameArray.getJSONObject(i).getInt("id");
                            subjectData.add(new SearchData(className, class_id));

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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

        return subjectData;
    }


}
