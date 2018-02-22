package com.example.a685559.poi.response;

import com.google.gson.annotations.SerializedName;

import com.example.a685559.poi.InterestPoint;

import java.util.ArrayList;

public class ListResponse {

    @SerializedName("list")
    private ArrayList<InterestPoint> list = null;

    public ArrayList<InterestPoint> getList() {
        return list;
    }

    public void setList(ArrayList<InterestPoint> list) {
        this.list = list;
    }

}
