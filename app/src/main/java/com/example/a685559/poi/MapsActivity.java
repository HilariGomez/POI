package com.example.a685559.poi;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ArrayList<InterestPoint> poiList;

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (InterestPoint poi : poiList) {
            String lat = "0";
            String lng = "0";
            String geo = poi.getGeoCoordinates();
            boolean b = true;
            for (int i = 0; i < geo.length() && b; ++i) {
                if (geo.charAt(i) == ',') {
                    lat = geo.substring(0, i);
                    lng = geo.substring(i + 1, geo.length());
                    b = false;
                }
            }
            LatLng position = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            mMap.addMarker(new MarkerOptions().position(position).title(poi.getTitle()));
        }
        LatLng iniPos = new LatLng(41.391926, 2.165208);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iniPos));
        mMap.setMinZoomPreference(12);
    }
}
