package com.example.a685559.poi.response;

import com.example.a685559.poi.InterestPoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("points")
    Call<InterestPointList> getPointsOfInterest();

    @GET("points/{id}")
    Call<InterestPoint> getPoint(@Path("id") String id);
}
