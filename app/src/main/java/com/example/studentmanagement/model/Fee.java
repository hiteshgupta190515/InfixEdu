package com.example.studentmanagement.model;

public class Fee {

    private String title;
    private String dueDate;
    private String status;
    private int amount;
    private int paid;
    private int discount;
    private int fine;
    private int balance;

    public Fee(String title, String dueDate, String status, int amount, int paid, int discount, int fine, int balance) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.amount = amount;
        this.paid = paid;
        this.discount = discount;
        this.fine = fine;
        this.balance = balance;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
