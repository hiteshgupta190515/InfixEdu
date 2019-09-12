package com.infix.edu.model;

public class Visitor {

    private int id;
    private String visitor_id;
    private String name;
    private String phone;
    private String purpose;
    private String date;
    private String noOfVVisitor;
    private String url;
    private String in_time;
    private String out_time;

    public Visitor(int id, String visitor_id, String name, String phone, String purpose, String date
            , String noOfVVisitor, String url, String in_time, String out_time) {
        this.id = id;
        this.visitor_id = visitor_id;
        this.name = name;
        this.phone = phone;
        this.purpose = purpose;
        this.date = date;
        this.noOfVVisitor = noOfVVisitor;
        this.url = url;
        this.in_time = in_time;
        this.out_time = out_time;
    }

    public String getNoOfVVisitor() {
        return noOfVVisitor;
    }

    public void setNoOfVVisitor(String noOfVVisitor) {
        this.noOfVVisitor = noOfVVisitor;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }
}
