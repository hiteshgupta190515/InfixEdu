package com.infix.edu.model;

public class LeaveList {

    private String title;
    private String from;
    private String to;
    private String apply;
    private String status;

    public LeaveList(String title, String from, String to, String apply, String status) {
        this.title = title;
        this.from = from;
        this.to = to;
        this.apply = apply;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
