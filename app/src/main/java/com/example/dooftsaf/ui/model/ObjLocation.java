package com.example.dooftsaf.ui.model;

public class ObjLocation {
    double lat;
    double lng;

    public ObjLocation() {
    }

    public ObjLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
