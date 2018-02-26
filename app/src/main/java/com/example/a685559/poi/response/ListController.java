package com.example.a685559.poi.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.example.a685559.poi.listeners.PoiListListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListController implements Callback<InterestPointList> {

    private static final String BASE_URL = "https://t21services.herokuapp.com/";

    private PoiListListener listener;

    public ListController(PoiListListener listener) {
        this.listener = listener;
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Service service = retrofit.create(Service.class);
        Call<InterestPointList> call = service.getPointsOfInterest();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<InterestPointList> call, Response<InterestPointList> response) {
        if (response.isSuccessful()) {
            InterestPointList responseList = response.body();

            listener.onPointListSuccess(responseList.getList());
        } else {
            listener.onError();
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<InterestPointList> call, Throwable t) {
        listener.onError();
        t.printStackTrace();
    }

}
