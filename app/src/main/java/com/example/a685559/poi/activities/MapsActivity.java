package com.example.a685559.poi.activities;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.OurClusterRenderer;
import com.example.a685559.poi.listeners.PoiListListener;
import com.example.a685559.poi.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, PoiListListener {

    private GoogleMap mMap;

    ArrayList<InterestPoint> poiList;

    ClusterManager clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle b = getIntent().getExtras();
        poiList = (ArrayList<InterestPoint>) b.get("POILIST");
    }

    public void initCluster() {
        clusterManager = new ClusterManager<>(getApplicationContext(), mMap);
        clusterManager.setRenderer(new OurClusterRenderer(getApplicationContext(), mMap, clusterManager));
        mMap.setOnCameraChangeListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(clusterManager);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String title = marker.getTitle();
                String id = "1";
                boolean b = true;
                int i = 0;
                while (i < title.length() && title.charAt(i) > '0' && title.charAt(i) <= '9') {
                    ++i;
                }
                id = title.substring(0, i);
                onPointDetail(id);
            }
        });

        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener() {
            @Override
            public boolean onClusterClick(Cluster cluster) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        cluster.getPosition(), (float) Math.floor(mMap
                                .getCameraPosition().zoom + 1)), 300,
                        null);
                return true;
            }
        });
    }

    public void loadMapData() {
        clusterManager.clearItems();
        for (InterestPoint poi : poiList) {
            InterestPoint poi2 = poi;
            poi2.setTitle(poi.getId() + " " + poi.getTitle());
            clusterManager.addItem(poi2);
        }
        clusterManager.cluster();
    }

    public void setStartPosition() {
        LatLng iniPos = new LatLng(41.391926, 2.165208);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iniPos));
        mMap.setMinZoomPreference(12);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initCluster();
        loadMapData();
        setStartPosition();
    }

    @Override
    public void onPointListSuccess(ArrayList<InterestPoint> poiList) {

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
