package com.example.a685559.poi.activities;

import com.example.a685559.poi.R;
import com.example.a685559.poi.fragments.MapsFragment;
import com.example.a685559.poi.fragments.OnDetailFragment;
import com.example.a685559.poi.fragments.PoiListFragment;
import com.example.a685559.poi.listeners.OnPoiSelectedListener;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnPoiSelectedListener {

    private Menu menu;

    Bundle poiListArgs = new Bundle();

    boolean isListFragmentActive = true;

    FragmentManager fragmentManager = getSupportFragmentManager();

    PoiListFragment poiListFragment = new PoiListFragment();

    static boolean detailActionBar = false;

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_item_map) {
            if (isListFragmentActive) {
                if (poiListFragment.getPoiList() != null) {
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_view_list_white_36dp));
                    isListFragmentActive = false;
                    poiListArgs.putSerializable("POILIST", poiListFragment.getPoiList());
                    MapsFragment mapFragment = new MapsFragment();
                    mapFragment.setArguments(poiListArgs);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, mapFragment);
                    fragmentTransaction.commit();
                }
            } else {
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_map_white_36dp));
                isListFragmentActive = true;

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, poiListFragment);
                fragmentTransaction.commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPoiSelected(String id) {
        Bundle poiBundle = new Bundle();
        poiBundle.putSerializable("ID", id);
        menu.getItem(0).setVisible(false);

        detailActionBar = true;

        OnDetailFragment onDetailFragment = new OnDetailFragment();
        onDetailFragment.setArguments(poiBundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, onDetailFragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        detailActionBar = false;
        getSupportActionBar().setDisplayHomeAsUpEnabled(detailActionBar);
        menu.getItem(0).setVisible(true);
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        detailActionBar = false;
        getSupportActionBar().setDisplayHomeAsUpEnabled(detailActionBar);
        menu.getItem(0).setVisible(true);
        super.onBackPressed();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("detailActionBar", detailActionBar);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        detailActionBar = savedInstanceState.getBoolean("detailActionBar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(detailActionBar);
        //menu.getItem(0).setVisible(!detailActionBar);
    }
}
