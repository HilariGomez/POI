package com.example.a685559.poi.listeners;

import com.example.a685559.poi.InterestPoint;

import java.util.ArrayList;

public interface PoiListListener {
    void onPointListSuccess(ArrayList<InterestPoint> poiList);

    void onPointDetail(String id);

    void onError();
}
