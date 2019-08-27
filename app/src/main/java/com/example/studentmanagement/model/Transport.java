package com.example.studentmanagement.model;

public class Transport {

    private String route;
    private String vehicle_no;
    private String vehicle_model;
    private String made_year;
    private String driver_name;
    private String mobile;
    private String driving_license;
    private String driver_contact;

    public Transport(String route, String vehicle_no, String vehicle_model, String made_year, String driver_name, String mobile,
                     String driving_license, String driver_contact) {
        this.route = route;
        this.vehicle_no = vehicle_no;
        this.vehicle_model = vehicle_model;
        this.made_year = made_year;
        this.driver_name = driver_name;
        this.mobile = mobile;
        this.driving_license = driving_license;
        this.driver_contact = driver_contact;
    }

    public String getDriver_contact() {
        return driver_contact;
    }

    public void setDriver_contact(String driver_contact) {
        this.driver_contact = driver_contact;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getMade_year() {
        return made_year;
    }

    public void setMade_year(String made_year) {
        this.made_year = made_year;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDriving_license() {
        return driving_license;
    }

    public void setDriving_license(String driving_license) {
        this.driving_license = driving_license;
    }
}
