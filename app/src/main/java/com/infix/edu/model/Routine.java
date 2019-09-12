package com.infix.edu.model;

public class Routine {

    private String startTime;
    private String endTime;
    private String subject;
    private String roomNo;
    private String weekName;

    public Routine(String startTime, String endTime, String subject, String roomNo, String weekName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.roomNo = roomNo;
        this.weekName = weekName;
    }

    public Routine(String startTime, String subject, String roomNo, String weekName) {
        this.startTime = startTime;
        this.subject = subject;
        this.roomNo = roomNo;
        this.weekName = weekName;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
