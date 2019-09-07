package com.infix.studentmanagement.model;

public class BookIssue {

    private String issue_date;
    private String return_date;
    private String book_no;
    private String status;
    private String title;
    private String subTitle;

    public BookIssue(String issue_date, String return_date, String book_no, String status, String title, String subTitle) {
        this.issue_date = issue_date;
        this.return_date = return_date;
        this.book_no = book_no;
        this.status = status;
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getBook_no() {
        return book_no;
    }

    public void setBook_no(String book_no) {
        this.book_no = book_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
