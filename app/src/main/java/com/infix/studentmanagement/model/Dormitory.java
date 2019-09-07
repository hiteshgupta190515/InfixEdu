package com.infix.studentmanagement.model;

public class Dormitory {

    private String title;
    private String room_no;
    private int no_of_bed;
    private String cost;
    private int status;

    public Dormitory(String title, String room_no, int no_of_bed, String cost, int status) {
        this.title = title;
        this.room_no = room_no;
        this.no_of_bed = no_of_bed;
        this.cost = cost;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public int getNo_of_bed() {
        return no_of_bed;
    }

    public void setNo_of_bed(int no_of_bed) {
        this.no_of_bed = no_of_bed;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
