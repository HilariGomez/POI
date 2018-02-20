package com.example.a685559.poi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListController.PoiListListener {

    private Menu menu;

    private Adapter mAdapter;

    Bundle poiListArgs = new Bundle();

    private SwipeRefreshLayout swipeRefreshLayout;

    boolean isListFragmentActive = true;

    boolean isListLoaded = false;

    FragmentManager fragmentManager = getSupportFragmentManager();

    PoiListFragment poiListFragment = new PoiListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        fetchList();
                    }
                }
        );
        fetchList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_item_map) {
            if (isListLoaded) {
                if (isListFragmentActive) {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_view_list_white_36dp));
                    isListFragmentActive = false;
                    poiListArgs.putSerializable("POILIST", poiListFragment.getPoiList());

                    MapsFragment mapFragment = new MapsFragment();
                    mapFragment.setArguments(poiListArgs);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, mapFragment);
                    fragmentTransaction.commit();
                } else {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_map_white_36dp));
                    isListFragmentActive = true;

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, poiListFragment);
                    fragmentTransaction.commit();
                }
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
        swipeRefreshLayout.setRefreshing(false);
        isListLoaded = true;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, poiListFragment);
        fragmentTransaction.commit();
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
