package com.example.studentmanagement.model;

public class HomeWork {

    private String homeworkDate;
    private String submissionDate;
    private String evaluationdate;
    private String file;
    private String marks;
    private String status;
    private String subject;
    private String title;

    public HomeWork(String homeworkDate, String submissionDate, String evaluationdate, String file, String marks, String status, String subject, String title) {
        this.homeworkDate = homeworkDate;
        this.submissionDate = submissionDate;
        this.evaluationdate = evaluationdate;
        this.file = file;
        this.marks = marks;
        this.status = status;
        this.subject = subject;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomeworkDate() {
        return homeworkDate;
    }

    public void setHomeworkDate(String homeworkDate) {
        this.homeworkDate = homeworkDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getEvaluationdate() {
        return evaluationdate;
    }

    public void setEvaluationdate(String evaluationdate) {
        this.evaluationdate = evaluationdate;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
