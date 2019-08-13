package com.example.studentmanagement.myconfig;

public class MyConfig {

    final public static String BASE_URL = "http://infixedu.com/api/";
    final public static String STUDENT_LIST = BASE_URL+"student-list";
    final public static String BOOK_LIST = BASE_URL+"book-list";

    public static String getLoginUrl(String email,String password){

        return BASE_URL+"login?email="+email+"&password="+password;
    }

}
