package com.example.studentmanagement.model;

public class OnlineExam {

    private String exam_title;
    private String subject;
    private String date;
    private int status;

    public OnlineExam(String exam_title, String subject, String date, int status) {
        this.exam_title = exam_title;
        this.subject = subject;
        this.date = date;
        this.status = status;
    }

    public String getExam_title() {
        return exam_title;
    }

    public void setExam_title(String exam_title) {
        this.exam_title = exam_title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
