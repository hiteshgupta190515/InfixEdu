package com.infix.studentmanagement.model;

public class Complaint {

    private int id;
    private int type;
    private int source;
    private int createdby;
    private String byName;
    private String action;
    private String phone;
    private String date;
    private String description;
    private String url;
    private String assigned;

    public Complaint(int id, int type, int source, int createdby, String byName, String action,
                     String phone, String date, String description, String url, String assigned) {
        this.id = id;
        this.type = type;
        this.source = source;
        this.createdby = createdby;
        this.byName = byName;
        this.action = action;
        this.phone = phone;
        this.date = date;
        this.description = description;
        this.url = url;
        this.assigned = assigned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getCreatedby() {
        return createdby;
    }

    public void setCreatedby(int createdby) {
        this.createdby = createdby;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }
}
