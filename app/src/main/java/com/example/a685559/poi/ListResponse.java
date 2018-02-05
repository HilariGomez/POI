package com.example.a685559.poi;

import com.google.gson.annotations.SerializedName;

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
