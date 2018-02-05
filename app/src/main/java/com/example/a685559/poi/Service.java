package com.example.a685559.poi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("points")
    Call<ListResponse> getPointsOfInterest();

    @GET("points/{id}")
    Call<ListResponse> getPoint(@Path("id") String id);
}
