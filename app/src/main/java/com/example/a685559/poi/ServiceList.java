package com.example.a685559.poi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceList {
    @GET("points")
    Call<ListResponse> getPointsOfInterest();
}
