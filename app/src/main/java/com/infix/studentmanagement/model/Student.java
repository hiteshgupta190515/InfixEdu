package com.infix.studentmanagement.model;

public class Student {

    private int id;
    private int school_id;
    private int class_id;
    private int student_category_id;
    private int section_id;
    private int session_id;
    private int user_id;
    private int parent_id;
    private int gender_id;
    private int admission_no;
    private int roll_no;
    private String first_name;
    private String last_name;
    private String full_name;
    private String date_of_birth;
    private String caste;
    private String email;
    private String mobile;
    private String admission_date;
    private String student_photo;
    private String height;
    private String weight;
    private String current_address;
    private String permanent_address;
    private int bloodgroup_id;
    private int religion_id;

    public Student(int id, int school_id, int class_id, int student_category_id, int section_id, int session_id, int user_id, int parent_id, int gender_id, int admission_no, int roll_no, String first_name, String last_name, String full_name, String date_of_birth, String caste, String email, String mobile, String admission_date, String student_photo, String height, String weight, String current_address, String permanent_address, int bloodgroup_id, int religion_id) {
        this.id = id;
        this.school_id = school_id;
        this.class_id = class_id;
        this.student_category_id = student_category_id;
        this.section_id = section_id;
        this.session_id = session_id;
        this.user_id = user_id;
        this.parent_id = parent_id;
        this.gender_id = gender_id;
        this.admission_no = admission_no;
        this.roll_no = roll_no;
        this.first_name = first_name;
        this.last_name = last_name;
        this.full_name = full_name;
        this.date_of_birth = date_of_birth;
        this.caste = caste;
        this.email = email;
        this.mobile = mobile;
        this.admission_date = admission_date;
        this.student_photo = student_photo;
        this.height = height;
        this.weight = weight;
        this.current_address = current_address;
        this.permanent_address = permanent_address;
        this.bloodgroup_id = bloodgroup_id;
        this.religion_id = religion_id;
    }

    public Student(String full_name, String email, String student_photo) {
        this.full_name = full_name;
        this.email = email;
        this.student_photo = student_photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getStudent_category_id() {
        return student_category_id;
    }

    public void setStudent_category_id(int student_category_id) {
        this.student_category_id = student_category_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public int getAdmission_no() {
        return admission_no;
    }

    public void setAdmission_no(int admission_no) {
        this.admission_no = admission_no;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(String admission_date) {
        this.admission_date = admission_date;
    }

    public String getStudent_photo() {
        return student_photo;
    }

    public void setStudent_photo(String student_photo) {
        this.student_photo = student_photo;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public int getBloodgroup_id() {
        return bloodgroup_id;
    }

    public void setBloodgroup_id(int bloodgroup_id) {
        this.bloodgroup_id = bloodgroup_id;
    }

    public int getReligion_id() {
        return religion_id;
    }

    public void setReligion_id(int religion_id) {
        this.religion_id = religion_id;
    }
}
