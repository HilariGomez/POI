package com.example.a685559.poi.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.example.a685559.poi.listeners.PoiListListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListController implements Callback<ListResponse> {

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
        Call<ListResponse> call = service.getPointsOfInterest();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
        if (response.isSuccessful()) {
            ListResponse responseList = response.body();

            listener.onPointListSuccess(responseList.getList());
        } else {
            listener.onError();
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<ListResponse> call, Throwable t) {
        listener.onError();
        t.printStackTrace();
    }

}
