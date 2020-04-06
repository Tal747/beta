package com.tal.beta;

public class User {
    private String name, email, phone, gid;
    private boolean uType;

    public User() {
    }

    public User(String name, String email, String phone, boolean uType, String gid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.uType = uType;
        this.gid = gid;
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

    public boolean getuType() { return uType;}

    public void setuType(boolean uType) {this.uType = uType;}

    public String getUid() {
        return gid;
    }

    public void setUid(String uid) {
        this.gid = uid;
    }
}