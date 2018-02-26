package com.example.a685559.poi.activities;

import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.R;
import com.example.a685559.poi.fragments.MapsFragment;
import com.example.a685559.poi.fragments.OnDetailFragment;
import com.example.a685559.poi.fragments.PoiListFragment;
import com.example.a685559.poi.listeners.OnPoiSelectedListener;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnPoiSelectedListener {

    private Menu menu;

    Bundle poiListArgs = new Bundle();

    boolean isListFragmentActive = true;

    FragmentManager fragmentManager = getSupportFragmentManager();

    PoiListFragment poiListFragment = new PoiListFragment();

    ArrayList<InterestPoint> poiList;

    State menuState = State.LIST;

    public enum State {
        LIST, MAP, DETAIL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, poiListFragment, "listTag");
            fragmentTransaction.commit();
        } else {
            poiListFragment = (PoiListFragment) getSupportFragmentManager().findFragmentByTag("listTag");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        updateMenu(menuState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_item_map) {
            if (poiListFragment.getPoiList() != null) {
                isListFragmentActive = false;

                poiListArgs.putSerializable("POILIST", poiListFragment.getPoiList());
                MapsFragment mapFragment = new MapsFragment();
                mapFragment.setArguments(poiListArgs);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, mapFragment);
                fragmentTransaction.commit();

                menuState = State.MAP;
                updateMenu(menuState);
            }
        } else if (id == R.id.action_item_list) {
            isListFragmentActive = true;

            poiListFragment = new PoiListFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, poiListFragment, "listTag");
            fragmentTransaction.commit();

            menuState = State.LIST;
            updateMenu(menuState);
        } else if (id==android.R.id.home) {
            onBackPressed();
            return true;
        }
        return true;
    }

    @Override
    public void onPoiSelected(String id) {
        Bundle poiBundle = new Bundle();
        poiBundle.putSerializable("ID", id);

        menuState = State.DETAIL;
        updateMenu(menuState);

        OnDetailFragment onDetailFragment = new OnDetailFragment();
        onDetailFragment.setArguments(poiBundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, onDetailFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (isListFragmentActive) {
            menuState = State.LIST;
        } else {
            menuState = State.MAP;
        }
        updateMenu(menuState);
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("listActive", isListFragmentActive);
        savedInstanceState.putString("menuState", menuState.toString());
    }

    public void recoverState(String savedState){
        if(savedState.equals("LIST")){
            menuState = State.LIST;
        } else if (savedState.equals("MAP")) {
            menuState = State.MAP;
        } else if (savedState.equals("DETAIL")) {
            menuState = State.DETAIL;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isListFragmentActive = savedInstanceState.getBoolean("listActive");
        String savedState = savedInstanceState.getString("menuState");
        recoverState(savedState);
    }

    public void updateMenu(State state) {
        if (state == State.DETAIL) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            menu.findItem(R.id.action_item_map).setVisible(false);
            menu.findItem(R.id.action_item_list).setVisible(false);

        } else if (state == State.LIST) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            menu.findItem(R.id.action_item_map).setVisible(true);
            menu.findItem(R.id.action_item_list).setVisible(false);

        } else if (state == State.MAP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            menu.findItem(R.id.action_item_map).setVisible(false);
            menu.findItem(R.id.action_item_list).setVisible(true);
        }
    }
}

