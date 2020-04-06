package com.tal.beta;

import java.util.List;

public class Group {
    private String name, date, id, creator;
    private boolean active, prvt;

    public Group(String name, String date, String id, boolean active, boolean prvt, String creator){
        this.name = name;
        this.date = date;
        this.id = id;
        this.active = active;
        this.prvt = prvt;
        this.creator = creator;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public boolean getActive() {return active;}

    public void setActive(boolean active) {this.active = active;}

    public boolean getPrvt() {return prvt;}

    public void setPrvt(boolean prvt) {this.prvt = prvt;}

    public String getCreator() {return creator;}

    public void setCreator(String creator) {this.creator = creator;}
}
