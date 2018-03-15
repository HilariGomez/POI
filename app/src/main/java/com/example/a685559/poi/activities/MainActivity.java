package com.example.a685559.poi.activities;

import com.example.a685559.poi.R;
import com.example.a685559.poi.fragments.MapsFragment;
import com.example.a685559.poi.fragments.OnDetailFragment;
import com.example.a685559.poi.fragments.PoiListFragment;
import com.example.a685559.poi.listeners.OnPoiSelectedListener;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPoiSelectedListener {

    private Menu menu;

    FragmentManager fragmentManager = getSupportFragmentManager();

    PoiListFragment poiListFragment;

    MapsFragment mapsFragment;

    State currentState = State.LIST;

    public enum State {
        LIST, MAP, DETAIL
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initFragment(currentState);
            launchFragment(currentState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        updateMenu(currentState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_item_map && poiListFragment.getPoiList() != null) {
            currentState = State.MAP;
            initFragment(currentState);
            launchFragment(currentState);
            updateMenu(currentState);
        } else if (id == R.id.action_item_list) {
            currentState = State.LIST;
            initFragment(currentState);
            launchFragment(currentState);
            updateMenu(currentState);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onPoiSelected(String id) {
        Bundle poiBundle = new Bundle();
        poiBundle.putSerializable("ID", id);

        OnDetailFragment onDetailFragment = new OnDetailFragment();
        onDetailFragment.setArguments(poiBundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isOrientationPortrait()) {
            fragmentTransaction.add(R.id.frameLayoutListMap, onDetailFragment);
            fragmentTransaction.addToBackStack(null);
            currentState = State.DETAIL;
            updateMenu(currentState);
        } else {
            fragmentTransaction.replace(R.id.frameLayoutDetail, onDetailFragment);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        goToPreviousState();
        updateMenu(currentState);
    }

    public void goToPreviousState() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
        List<Fragment> activeFragments = fragmentManager.getFragments();
        Fragment currentFragment = activeFragments.get(0);
        if (currentFragment instanceof PoiListFragment) {
            currentState = State.LIST;
        } else if (currentFragment instanceof MapsFragment) {
            currentState = State.MAP;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("currentState", currentState.toString());
    }

    public void recoverState(String savedState) {
        if (savedState.equals("LIST")) {
            currentState = State.LIST;
        } else if (savedState.equals("MAP")) {
            currentState = State.MAP;
        } else if (savedState.equals("DETAIL")) {
            if (isOrientationPortrait()) {
                currentState = State.DETAIL;
            } else {
                goToPreviousState();
            }
        }
    }

    public void recoverFragments() {
        if (currentState == State.LIST) {
            poiListFragment = (PoiListFragment) getSupportFragmentManager().findFragmentByTag("list");
        } else if (currentState == State.MAP) {
            mapsFragment = (MapsFragment) getSupportFragmentManager().findFragmentByTag("map");
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recoverState(savedInstanceState.getString("currentState"));
        recoverFragments();
    }

    public void initFragment(State state) {
        Bundle fragmentArgs = new Bundle();
        if (state == State.MAP) {
            mapsFragment = new MapsFragment();
            fragmentArgs.putSerializable("POILIST", poiListFragment.getPoiList());
            fragmentArgs.putString("SELECTEDPOI", poiListFragment.getPoiSelectedId());
            mapsFragment.setArguments(fragmentArgs);
        } else if (state == State.LIST) {
            poiListFragment = new PoiListFragment();
            if (mapsFragment != null) {
                fragmentArgs.putSerializable("POILIST", mapsFragment.getPoiList());
                fragmentArgs.putString("SELECTEDPOI", mapsFragment.getPoiSelectedId());
                poiListFragment.setArguments(fragmentArgs);
            }
        }
    }

    public void launchFragment(State state) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (state == State.MAP) {
            fragmentTransaction.replace(R.id.frameLayoutListMap, mapsFragment, "map");
        } else if (state == State.LIST) {
            fragmentTransaction.replace(R.id.frameLayoutListMap, poiListFragment, "list");
        }
        fragmentTransaction.commit();
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

    public boolean isOrientationPortrait() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }
        return false;
    }
}
