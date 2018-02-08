package com.example.a685559.poi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListController.PoiListListener {

    private RecyclerView recyclerView;

    private Adapter mAdapter;

    private ArrayList<InterestPoint> poiList;

    private SwipeRefreshLayout swipeRefreshLayout;

    boolean b = false;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_item_map) {
            if (b) {
                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("POILIST", this.poiList);
                startActivity(i);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchList() {
        ListController listController = new ListController(this);
        listController.start();
    }

    @Override
    public void onPointListSuccess(ArrayList<InterestPoint> poiList) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        this.poiList = poiList;
        b = true;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new Adapter(poiList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onPointDetail(String id) {
        Intent i = new Intent(this, OnDetailActivity.class);
        i.putExtra("ID", id);
        startActivity(i);
    }


    @Override
    public void onError() {

    }

}
