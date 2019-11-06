package com.infix.edu.myconfig;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.infix.edu.R;
import com.infix.edu.activity.AddBookActivity;
import com.infix.edu.activity.AddHomeWorkActivity;
import com.infix.edu.activity.AdminStaffListActivity;
import com.infix.edu.adapter.StaffListAdapter;
import com.infix.edu.adapter.StudentListAdapter;
import com.infix.edu.model.SearchData;
import com.infix.edu.model.StaffData;
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


    public boolean send_book_data(final Activity activity,String title,String category_id,String book_no, String isbn,String publisher_name,String author_name,String subject_id,String reck_no,String quantity,String price,String details,String date,String user_id) {

        final boolean[] isSuccess = {false};

        final ArrayList<SearchData> subjectData = new ArrayList<>();
        subjectData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.addBook(title,category_id,book_no,isbn,publisher_name,author_name,subject_id,reck_no,quantity,price,details,date,user_id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {
                        isSuccess[0] = true;
                       showSuccess("Book added successfully",activity);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(activity);
        req.add(request);

        return isSuccess[0];
    }

    private void showSuccess(String message, final Activity activity){


        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity,R.style.DialogTheme);
        View mView = LayoutInflater.from(activity).inflate(R.layout.pssword_change_success, null);

        TextView textView = mView.findViewById(R.id.txt_message_body);
        textView.setText(message);

        alertBuilder.setView(mView);
        final AlertDialog dialog = alertBuilder.create();

        Rect displayRectangle = new Rect();
        Window window = dialog.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        mView.setMinimumWidth((int)(displayRectangle.width()));
        mView.setMinimumHeight((int)(displayRectangle.height() * 0.5f));

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
                dialog.dismiss();
            }
        },3000);

    }

    public ArrayList<SearchData> getAllFeesList(final Context ctx) {

        final ArrayList<SearchData> feesData = new ArrayList<>();
        feesData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.FEES_GROUP, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONArray classNameArray = response.getJSONArray("data");

                        for (int i = 0; i < classNameArray.length(); i++) {

                            String className = classNameArray.getJSONObject(i).getString("name")+"="+classNameArray.getJSONObject(i).getString("description");
                            int class_id = classNameArray.getJSONObject(i).getInt("id");
                            feesData.add(new SearchData(className, class_id));

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

        return feesData;

    }

    public boolean update_fees_data(String name,String description, String id,final Activity activity) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.fees_data_update(name,description,id), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        isSuccess = true;
                        showSuccess("fees updated!",activity);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(activity);
        req.add(request);

        return isSuccess;
    }

    public boolean sent_fees_data(String name,String description,final Activity activity) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.fees_data_send(name,description), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        isSuccess = true;
                        showSuccess("fees created!",activity);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue req = Volley.newRequestQueue(activity);
        req.add(request);

        return isSuccess;
    }


    public ArrayList<SearchData> getAllMemberType(final Context ctx) {

        final ArrayList<SearchData> classData = new ArrayList<>();
        classData.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.ROLE, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getBoolean("success")) {

                        JSONArray catArray = response.getJSONArray("data");

                        for (int i = 0; i < catArray.length(); i++) {

                            String className = catArray.getJSONObject(i).getString("name");
                            int class_id = catArray.getJSONObject(i).getInt("id");
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

    public ArrayList<SearchData> getAllStaff(int type, final Context ctx){

        final ArrayList<SearchData> staffs = new ArrayList<>();
        staffs.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MyConfig.getAllStaff(type), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getBoolean("success")) {

                        JSONArray staffArray = response.getJSONArray("data");

                        for (int i = 0; i < staffArray.length(); i++) {
                            String name = staffArray.getJSONObject(i).getString("full_name");
                            int id = staffArray.getJSONObject(i).getInt("user_id");
                            staffs.add(new SearchData(name, id));
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

        return staffs;
    }

}
