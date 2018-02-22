package com.example.a685559.poi.listeners;

import com.example.a685559.poi.InterestPoint;

public interface PointOfInterestListener {
    void onPointDetailSuccess(InterestPoint poi);

    void onError();
}
