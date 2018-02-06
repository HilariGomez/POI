package com.example.a685559.poi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InterestPoint implements Serializable {

    private String id;

    private String title;

    private String address;

    private String transport;

    private String email;

    @SerializedName("geocoordinates")
    private String geoCoordinates;

    private String description;

    private String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(String geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InterestPoint{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", transport='").append(transport).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", geoCoordinates='").append(geoCoordinates).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
