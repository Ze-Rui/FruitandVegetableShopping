package com.example.shopping.location.bean;

public class Location {
    private int id;

    private String userName;

    private String phone;

    private String location;

    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Location() {
    }

    public Location(int id, String userName, String phone, String location, int userId) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.location = location;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}


