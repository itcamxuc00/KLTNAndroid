package com.example.dooftsaf.ui.model;

import android.location.Location;

public class User {
    private String id;
    private String name;
    private String address;
    private ObjLocation location;
    private String role;

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    private String activity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ObjLocation getLocation() {
        return location;
    }

    public void setLocation(ObjLocation location) {
        this.location = location;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
