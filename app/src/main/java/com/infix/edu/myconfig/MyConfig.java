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
    final public static String ADMIN_DORMITORY_ROOM_LIST = BASE_URL+"room-list";
    final public static String ADMIN_DORMITORY_LIST = BASE_URL+"dormitory-list";
    final public static String ADMIN_DORMITORY_ROOM_TYPE_LIST = BASE_URL+"room-type-list";
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
    final public static String PENDING_LEAVE = BASE_URL+"pending-leave";
    final public static String APPROVED_LEAVE = BASE_URL+"approved-leave";
    final public static String REJECTED_LEAVE = BASE_URL+"reject-leave";
    final public static String DRIVER_LIST = BASE_URL+"driver-list";
    final public static String STAFF_ROLE = BASE_URL+"staff-roles";
    final public static String BOOK_CATEGORY = BASE_URL+"book-category";
    final public static String SUBJECT_LIST = BASE_URL+"subject";
    final public static String FEES_GROUP = BASE_URL+"fees-group";
    final public static String ROLE = BASE_URL+"library-member-role";

    public static String getLoginUrl(String email,String password){

        return BASE_URL+"login?email="+email+"&password="+password;
    }
    public static String attendance_data_send(String id,String atten,String date){

        return BASE_URL+"student-attendance-store?id="+id+"&attendance="+atten+"&date="+date;
    }
    public static String add_library_member(String member_type,String member_ud_id,String cls_id,String sec_id,String student_id,String stuff_id,String created_by){
        return BASE_URL+"add-library-member?member_type="+member_type+"&member_ud_id="+member_ud_id+"&class="+cls_id+"&section="+sec_id+"&student="+student_id+"&staff="+stuff_id+"&created_by="+created_by;
    }
    public static String fees_data_send(String name,String description){
        return BASE_URL+"fees-group-store?name="+name+"&description="+description;
    }
    public static String fees_data_update(String name,String description,String id){
        return BASE_URL+"fees-group-update?name="+name+"&description="+description+"&id="+id;
    }

    public static String addDormitory(String name,String type,String intake, String address,String description){

        return BASE_URL+"add-dormitory?dormitory_name="+name+"&type="+type+"&intake="+intake+"&address="+address+"&description="+description;
    }
    public static String addVehicle(String vehicleNo,String model,String driver_id, String note,String year){

        return BASE_URL+"vehicle?vehicle_number="+vehicleNo+"&vehicle_model="+model+"&driver_id="+driver_id+"&note="+note+"&year_made="+year;

    }
    public static String addBook(String title,String category_id,String book_no, String isbn,String publisher_name,String author_name,String subject_id,String reck_no,String quantity,String price,String details,String date,String user_id){

        return BASE_URL+"save-book-data?book_title="+title+"&book_category_id="+category_id+"&book_number="+book_no+"&isbn_no="+isbn+"&publisher_name="+
                publisher_name+"&author_name="+author_name+"&subject_id="+subject_id+"&rack_number="+reck_no+"&quantity="+quantity+"&book_price="+price+"&details="+details+"&post_date="+date+"&user_id="+user_id;

    }
    public static String addRoute(String title,String fare){
        return BASE_URL+"transport-route?title="+title+"&far="+fare;
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
    public static String getAllStaff(int id){
        return BASE_URL+"staff-list/"+id;
    }
    public static String setLeaveStatus(int id,String status){
        return BASE_URL+"update-leave?id="+id+"&status="+status;
    }
    public static String setToken(int id,String token){
        return BASE_URL+"set-token?id="+id+"&token="+token;
    }
    public static String sentNotificationForAll(int id){
        return BASE_URL+"group-token?id="+id;
    }
    public static String sentNotification(String title,String body,String token){
        return ROOT_URL+"notification-api?send_notification&token="+token+"&body="+body+"&title="+title;
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
