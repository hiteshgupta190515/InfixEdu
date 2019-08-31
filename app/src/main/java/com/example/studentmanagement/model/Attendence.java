package com.example.studentmanagement.model;

public class Attendence {

    private String date;
    private String status;
    private int p_index;

    public Attendence(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public Attendence(String date, int p_index) {
        this.date = date;
        this.p_index = p_index;
    }

    public int getP_index() {
        return p_index;
    }

    public void setP_index(int p_index) {
        this.p_index = p_index;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
