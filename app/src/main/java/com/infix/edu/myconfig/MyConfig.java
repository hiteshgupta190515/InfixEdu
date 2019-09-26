package com.infix.edu.myconfig;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyConfig {

    final public static String ROOT_URL = "https://infixedu.com/android/";
    final public static String BASE_URL = ROOT_URL+"api/";
    final public static String STUDENT_LIST = BASE_URL+"student-list";
    final public static String STUDENT_DORMITORY_LIST = BASE_URL+"student-dormitory";
    final public static String STUDENT_TRANSPORT_LIST = BASE_URL+"student-transport-report";
    final public static String BOOK_LIST = BASE_URL+"book-list";
    final public static String INVENTORY_LIST = BASE_URL+"item-list";
    final public static String VISITOR_LIST = BASE_URL+"visitor";
    final public static String COMPLAINT_LIST = BASE_URL+"complaint";
    final public static String ABOUT = BASE_URL+"parent-about";
    final public static String UPLOAD_HOMEWORK = BASE_URL+"add-homework";
    final public static String UPLOAD_CONTENT = BASE_URL+"teacher-upload-content";
    final public static String LEAVE_APPLY = BASE_URL+"staff-apply-leave";
    final public static String LEAVE_TYPE = BASE_URL+"staff-leave-type";

    public static String getLoginUrl(String email,String password){

        return BASE_URL+"login?email="+email+"&password="+password;
    }

    public static String isEnabled(){
        return BASE_URL+"is-enabled";
    }

    public static String getRoutineUrl(int id){
        return BASE_URL+"student-class-routine/"+id;
    }
    public static String getAllContent(){
        return BASE_URL+"content-list";
    }
    public static String deleteContent(int id){
        return BASE_URL+"delete-content/"+id;
    }
    public static String getLeaveList(int id){
        return BASE_URL+"staff-apply-list/"+id;
    }

    public static String getFeesUrl(int id){
        return BASE_URL+"fees-collect-student-wise/"+id;
    }
    public static String getzhomeWorksUrl(int id){
        return BASE_URL+"student-homework/"+id;
    }
    public static String getHomeWorkListUrl(int id){
        return BASE_URL+"homework-list/"+id;
    }
    public static String getSubjectsUrl(int id){
        return BASE_URL+"studentSubject/"+id;
    }
    public static String getParentChildList(int id){
        return BASE_URL+"child-list/"+id;
    }
    public static String getChildren(int id){
        return BASE_URL+"childInfo/"+id;
    }

    public static String getNoticeUrl(int id){
        return BASE_URL+"student-noticeboard/"+id;
    }
    public static String getOnlineExamNameUrl(int id){
        return BASE_URL+"student-online-exam/"+id;
    }
    public static String getOnlineExamChooseUrl(int id){
        return BASE_URL+"choose-exam/"+id;
    }
    public static String getStudentTeacherUrl(int id){
        return BASE_URL+"studentTeacher/"+id;
    }
    public static String getStudentIssuedBooks(int id){
        return BASE_URL+"student-library/"+id;
    }
    public static String getStudentTimeline(int id){
        return BASE_URL+"student-timeline/"+id;
    }
    public static String getStudentOnlineResult(int id,int exam_id){
        return BASE_URL+"online-exam-result/"+id+"/"+exam_id;
    }
    public static String getStudentClassExamResult(int id,int exam_id){
        return BASE_URL+"exam-result/"+id+"/"+exam_id;
    }
    public static String getStudentClassExamName(int id){
        return BASE_URL+"exam-list/"+id;
    }
    public static String getStudentAttendence(int id,int month,int year){
        return BASE_URL+"student-my-attendance/"+id+"?month="+month+"&year="+year;
    }

    public static String getTeacherAttendence(int id,int month,int year){
        return BASE_URL+"my-attendance/"+id+"?month="+month+"&year="+year;
    }

    public static String getStudentClsExamShedule(int id,int code){
        return BASE_URL+"exam-schedule/"+id+"/"+code;
    }
    public static String changePassword(int id,String current,String newP){
        return BASE_URL+"change-password?id="+id+"&current_password="+current+"&new_password="+newP;
    }
    public static String getStudentByClassAndSection(int mClass,int mSection){
        return BASE_URL+"search-student?section="+mSection+"&class="+mClass;
    }
    public static String getRoutineByClassAndSection(int id,int mClass,int mSection){
        return BASE_URL+"section-routine/"+id+"/"+mClass+"/"+mSection;
    }
    public static String getTeacherSubject(int id){
        return BASE_URL+"subject/"+id;
    }
    public static String getTeacherMyRoutine(int id){
        return BASE_URL+"my-routine/"+id;
    }
    public static String getStudentByClass(int mClass){
        return BASE_URL+"search-student?class="+mClass;
    }
    public static String getStudentByName(String name){
        return BASE_URL+"search-student?name="+name;
    }
    public static String getStudentByRoll(String roll){
        return BASE_URL+"search-student?roll_no="+roll;
    }
    public static String getClassAndSection(){
        return BASE_URL+"class";
    }

    public static void getProfileImage(String url, CircleImageView image, Context ctx){
        Glide.with(ctx)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(image);
    }

    public static String getInventory(int id){

        String category;

        switch (id){

            case 1:

                category = "Raw Materials Inventory";
                break;
            case 2:
                    category = "Transit Inventory";
                    break;
            case 3:
                category = "Buffer Inventory";
                break;
            case 4:
                category = "Application Inventory";
                break;
            case 5:
                category = "Enterprice Inventory";
            case 6:
                category = "Others Inventory";
                default:
                    category = "Others Inventory";
                    break;

        }
return category;
    }

    public static String getComplaintType(int id){

        String category;

        switch (id){

            case 1:

                category = "Raw Materials Inventory";
                break;
            case 2:
                category = "Transit Inventory";
                break;
            case 3:
                category = "Buffer Inventory";
                break;
            case 4:
                category = "Application Inventory";
                break;
            case 5:
                category = "Enterprice Inventory";
            case 6:
                category = "Others Inventory";
            default:
                category = "Others Inventory";
                break;

        }
        return category;
    }

    public static String getMonth(int id){

        String category = null;

        switch (id){

            case 1:
                category = "January";
                break;
            case 2:
                category = "February";
                break;
            case 3:
                category = "March";
                break;
            case 4:
                category = "April";
                break;
            case 5:
                category = "May";
                break;
            case 6:
                category = "June";
                break;
            case 7:
                category = "July";
                break;
            case 8:
                category = "August";
                break;
            case 9:
                category = "September";
                break;
            case 10:
                category = "October";
                break;
            case 11:
                category = "November";
                break;
            case 12:
                category = "December ";
                break;
        }
        return category;
    }

    public static int getWeekDay(String week){

        int category;

        switch (week){

            case "Mon":

                category = 0;
                break;
            case "Tue":
                category = 1;
                break;
            case "Wed":
                category = 2;
                break;
            case "Thu":
                category = 3;
                break;
            case "Fri":
                category = 4;
            case "Sat":
                category = 5;
            case "Sun":
                category = 6;
            default:
                category = 0;
                break;
        }
        return category;
    }

    public static String getUserRole(int id){

        String category;

        switch (id){

            case 1:
                category = "Super admin";
                break;
            case 2:
                category = "Student";
                break;
            case 3:
                category = "Parents";
                break;
            case 4:
                category = "Teacher";
                break;
            case 5:
                category = "Admin";
            case 6:
                category = "Accountant";
            default:
                category = "Others";
                break;

        }
        return category;
    }

    public static String getStudentClass(int id){

        String category;

        switch (id){

            case 1:
                category = "Super admin";
                break;
            case 2:
                category = "Student";
                break;
            case 3:
                category = "Parents";
                break;
            case 4:
                category = "Teacher";
                break;
            case 5:
                category = "Admin";
            case 6:
                category = "Accountant";
            default:
                category = "Others";
                break;

        }
        return category;
    }

    public static String getAmPm(String time){

        String[] parts = time.split(":");
        String part1 = parts[0];
        String part2 = parts[1];

        int hr = Integer.parseInt(part1);
        int min = Integer.parseInt(part2);

        if(hr <= 12){
            return hr+":"+min+" ÄM ";
        }else{
            return hr+":"+min+" PM ";
        }

    }

    public static String selectDate(int day, int month, int year) {

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

}
