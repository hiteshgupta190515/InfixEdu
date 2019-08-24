package com.example.studentmanagement.myconfig;

public class MyConfig {

    final public static String BASE_URL = "http://infixedu.com/api/";
    final public static String ROOT_URL = "http://infixedu.com/";
    final public static String STUDENT_LIST = BASE_URL+"student-list";
    final public static String BOOK_LIST = BASE_URL+"book-list";
    final public static String INVENTORY_LIST = BASE_URL+"item-list";
    final public static String VISITOR_LIST = BASE_URL+"visitor";
    final public static String COMPLAINT_LIST = BASE_URL+"complaint";

    public static String getLoginUrl(String email,String password){

        return BASE_URL+"login?email="+email+"&password="+password;
    }

    public static String getRoutineUrl(int id){
        return BASE_URL+"student-class-routine/"+id;
    }

    public static String getFeesUrl(int id){
        return BASE_URL+"fees-collect-student-wise/"+id;
    }
    public static String getzhomeWorksUrl(int id){
        return BASE_URL+"student-noticeboard/"+id;
    }
    public static String getSubjectsUrl(int id){
        return BASE_URL+"studentSubject/"+id;
    }

    public static String getNoticeUrl(int id){
        return BASE_URL+"student-homework/"+id;
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

}
