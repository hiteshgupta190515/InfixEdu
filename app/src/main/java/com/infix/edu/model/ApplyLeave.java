package com.infix.edu.model;

public class ApplyLeave {

    private String apply_date;
    private int leave_type;
    private String leave_from;
    private String leave_to;
    private int teacher_id;
    private String reason;

    public ApplyLeave(String apply_date, int leave_type, String leave_from, String leave_to, int teacher_id, String reason) {
        this.apply_date = apply_date;
        this.leave_type = leave_type;
        this.leave_from = leave_from;
        this.leave_to = leave_to;
        this.teacher_id = teacher_id;
        this.reason = reason;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public int getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(int leave_type) {
        this.leave_type = leave_type;
    }

    public String getLeave_from() {
        return leave_from;
    }

    public void setLeave_from(String leave_from) {
        this.leave_from = leave_from;
    }

    public String getLeave_to() {
        return leave_to;
    }

    public void setLeave_to(String leave_to) {
        this.leave_to = leave_to;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
