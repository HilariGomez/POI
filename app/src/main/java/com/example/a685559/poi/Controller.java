package com.example.a685559.poi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller implements Callback<ListResponse> {

    static final String BASE_URL = "https://t21services.herokuapp.com/";

    ArrayList<InterestPoint> poiList;

    public ArrayList<InterestPoint> getPoiList() {
        return poiList;
    }

    public void start(Integer num) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        if (num == 0) {
            ServiceList ServiceList = retrofit.create(ServiceList.class);
            Call<ListResponse> call = ServiceList.getPointsOfInterest();
            call.enqueue(this);
        } else {
            ServicePoint servicePoint = retrofit.create(ServicePoint.class);
            Call<ListResponse> call = servicePoint.getPoint(num.toString());
            call.enqueue(this);
        }

    }

    @Override
    public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
        if (response.isSuccessful()) {
            ListResponse responseList = response.body();
            poiList = responseList.getList();
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ListResponse> call, Throwable t) {
        t.printStackTrace();
    }
}