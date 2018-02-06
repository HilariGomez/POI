package com.example.a685559.poi;

/**
 * Created by A685559 on 06/02/2018.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterestPointController implements Callback<InterestPoint> {

    private static final String BASE_URL = "https://t21services.herokuapp.com/";

    private PointOfInterestListener listener;

    public InterestPointController(PointOfInterestListener listener) {
        this.listener = listener;
    }

    public void start(Integer num) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Service service = retrofit.create(Service.class);
        Call<InterestPoint> call = service.getPoint(num.toString());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<InterestPoint> call, Response<InterestPoint> response) {
        if (response.isSuccessful()) {
        } else {
            listener.onError();
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<InterestPoint> call, Throwable t) {
        listener.onError();
        t.printStackTrace();
    }

    public interface PointOfInterestListener {
        void onPointDetailSuccess(List<InterestPoint> poiList);

        void onError();
    }
}
