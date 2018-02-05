package com.example.a685559.poi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Controller.PointOfInterestListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller controller = new Controller(this);
        controller.start(0);
    }


    @Override
    public void onPointListSuccess(List<InterestPoint> poiList) {
        ArrayList<InterestPoint> poi = new ArrayList<>();
        poi.addAll(poiList);
        Intent i = new Intent(getApplicationContext(), POIListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("POILIST", poi);
        startActivity(i);
        finish();
    }

    @Override
    public void onPointDetailSuccess(InterestPoint interestPoint) {

    }

    @Override
    public void onError() {

    }
}
