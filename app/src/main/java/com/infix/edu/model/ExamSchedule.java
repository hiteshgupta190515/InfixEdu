package com.infix.edu.model;

public class ExamSchedule {

    private String examName;
    private String subjectName;
    private String date;
    private String roomNo;
    private String start;
    private String end;


    public ExamSchedule(String examName, String subjectName, String date, String roomNo, String start, String end) {
        this.examName = examName;
        this.subjectName = subjectName;
        this.date = date;
        this.roomNo = roomNo;
        this.start = start;
        this.end = end;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
