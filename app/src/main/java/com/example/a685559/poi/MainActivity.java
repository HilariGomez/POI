package com.example.a685559.poi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Controller.PointOfInterestListener {

    private RecyclerView recyclerView;

    private POIAdapter mAdapter;

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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new POIAdapter(poi);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPointDetailSuccess(InterestPoint interestPoint) {

    }

    @Override
    public void onError() {

    }
}
