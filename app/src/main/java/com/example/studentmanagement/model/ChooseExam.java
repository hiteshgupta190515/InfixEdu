package com.example.studentmanagement.model;

public class ChooseExam {

    private String examName;
    private int examId;

    public ChooseExam(String examName, int examId) {
        this.examName = examName;
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }
}
