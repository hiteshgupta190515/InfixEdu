package com.infix.edu.model;

public class Tstudent {

    private String name;
    private int roll;
    private String className;
    private String sectionName;
    private String image;
    private int id;

    public Tstudent(String name, int roll, String className, String sectionName, String image, int id) {
        this.name = name;
        this.roll = roll;
        this.className = className;
        this.sectionName = sectionName;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
