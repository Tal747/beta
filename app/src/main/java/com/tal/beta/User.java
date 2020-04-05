package com.tal.beta;

public class User {
    private String name, email, phone, uid;
    Boolean uType;

    public User() {
    }

    public User(String name, String email, String phone, Boolean uType, String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.uType = uType;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getuType() { return uType;}

    public void setuType(Boolean uType) {this.uType = uType;}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}