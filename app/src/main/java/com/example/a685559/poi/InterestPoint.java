package com.example.a685559.poi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InterestPoint implements Serializable {

    private String id;

    private String title;

    @SerializedName("geoCoordinates")
    private String geoCoordinates;


    public InterestPoint(String id, String title, String geoCoordinates) {
        this.id = id;
        this.title = title;
        this.geoCoordinates = geoCoordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(String geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }
}
