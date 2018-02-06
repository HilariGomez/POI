package com.example.a685559.poi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListController.PoiListListener {

    private RecyclerView recyclerView;

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListController listController = new ListController(this);
        listController.start();
    }


    @Override
    public void onPointListSuccess(List<InterestPoint> poiList) {
        ArrayList<InterestPoint> poi = new ArrayList<>();
        poi.addAll(poiList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new Adapter(poi, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onPointDetail(InterestPoint poi) {
        Intent i = new Intent(this, OnDetailActivity.class);
        i.putExtra("POI", poi);
        startActivity(i);
    }


    @Override
    public void onError() {

    }
}
