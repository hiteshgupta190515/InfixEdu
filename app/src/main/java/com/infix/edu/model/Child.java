package com.infix.edu.model;

public class Child {

    private String name;
    private String imgUrl;
    private String mClass;
    private String section;
    private int roll;
    private int id;

    public Child(String name, String imgUrl, String mClass, String section, int roll, int id) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.mClass = mClass;
        this.section = section;
        this.roll = roll;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
