package com.infix.edu.model;

public class AddHomeWork {

    private String assign_date;
    private String submission_date;
    private String description;
    private int classId;
    private int sectionId;
    private int subjectId;
    private int teacherId;

    public AddHomeWork(String assign_date, String submission_date, String description, int classId, int sectionId, int subjectId, int teacherId) {
        this.assign_date = assign_date;
        this.submission_date = submission_date;
        this.description = description;
        this.classId = classId;
        this.sectionId = sectionId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(String assign_date) {
        this.assign_date = assign_date;
    }

    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
