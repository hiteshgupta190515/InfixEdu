package com.infix.edu.model;

public class Subject {

    private String subject;
    private String teacher;
    private String type;
    private String code;

    public Subject(String subject, String teacher, String type, String code) {
        this.subject = subject;
        this.teacher = teacher;
        this.type = type;
        this.code = code;
    }

    public Subject(String subject, String teacher, String type) {
        this.subject = subject;
        this.teacher = teacher;
        this.type = type;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
