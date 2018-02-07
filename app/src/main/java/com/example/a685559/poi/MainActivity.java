package com.example.a685559.poi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListController.PoiListListener {

    private RecyclerView recyclerView;

    private Adapter mAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        fetchList();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        fetchList();
    }

    public void fetchList() {
        ListController listController = new ListController(this);
        listController.start();
    }

    @Override
    public void onPointListSuccess(ArrayList<InterestPoint> poiList) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new Adapter(poiList, this);
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
