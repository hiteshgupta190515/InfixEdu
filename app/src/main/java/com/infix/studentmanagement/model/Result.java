package com.infix.studentmanagement.model;

public class Result {

    private String grade;
    private int total_marks;
    private String subject_name;
    private int marks_obtain;
    private int marks_pass;
    private String exam_nme;

    public Result(String grade, int total_marks, String subject_name, int marks_obtain, int marks_pass, String exam_nme) {
        this.grade = grade;
        this.total_marks = total_marks;
        this.subject_name = subject_name;
        this.marks_obtain = marks_obtain;
        this.marks_pass = marks_pass;
        this.exam_nme = exam_nme;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(int total_marks) {
        this.total_marks = total_marks;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getMarks_obtain() {
        return marks_obtain;
    }

    public void setMarks_obtain(int marks_obtain) {
        this.marks_obtain = marks_obtain;
    }

    public int getMarks_pass() {
        return marks_pass;
    }

    public void setMarks_pass(int marks_pass) {
        this.marks_pass = marks_pass;
    }

    public String getExam_nme() {
        return exam_nme;
    }

    public void setExam_nme(String exam_nme) {
        this.exam_nme = exam_nme;
    }
}
