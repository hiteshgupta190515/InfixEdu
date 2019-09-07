package com.infix.studentmanagement.model;

public class Inventory {

    private int id;
    private int school_id;
    private String category_name;
    private int total_in_stock;
    private String description;
    private String name;

    public Inventory(int id, int school_id, String category_name, int total_in_stock, String description, String name) {
        this.id = id;
        this.school_id = school_id;
        this.category_name = category_name;
        this.total_in_stock = total_in_stock;
        this.description = description;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getTotal_in_stock() {
        return total_in_stock;
    }

    public void setTotal_in_stock(int total_in_stock) {
        this.total_in_stock = total_in_stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
