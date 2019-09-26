package com.infix.edu.model;

public class Content {

    private String title;
    private String url;
    private String type;
    private String date;
    private String classes;
    private int id;

    public Content(String title, String url, String type, String date, String classes, int id) {
        this.title = title;
        this.url = url;
        this.type = type;
        this.date = date;
        this.classes = classes;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
