package com.example.a685559.poi.fragments;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.OurClusterRenderer;
import com.example.a685559.poi.R;
import com.example.a685559.poi.listeners.OnPoiSelectedListener;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    private GoogleMap mMap;

    MapView mMapView;

    private OnPoiSelectedListener onPoiSelectedListener;

    ArrayList<InterestPoint> poiList;

    ClusterManager clusterManager;

    String startingPoiId = "none";

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onPoiSelectedListener = (OnPoiSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Listener");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.poiList = (ArrayList<InterestPoint>) bundle.getSerializable("POILIST");
            startingPoiId = bundle.getString("SELECTEDPOI");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                initCluster();
                loadMapData();
                setStartPosition();
            }
        });
        return rootView;
    }

    public void initCluster() {
        clusterManager = new ClusterManager<>(getActivity(), mMap);
        clusterManager.setRenderer(new OurClusterRenderer(getActivity(), mMap, clusterManager));
        mMap.setOnCameraChangeListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        mMap.setOnInfoWindowClickListener(clusterManager);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(getActivity());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getActivity());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                info.addView(title);

                return info;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String id = marker.getSnippet();
                onPoiSelectedListener.onPoiSelected(id);
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
            clusterManager.addItem(poi);
        }
        clusterManager.cluster();
    }

    public void setStartPosition() {
        mMap.setMinZoomPreference(12);
        if(startingPoiId.equals("none")) {
            LatLng iniPos = new LatLng(41.391926, 2.165208);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(iniPos));

        } else {
            InterestPoint startingPoi = poiList.get(Integer.valueOf(startingPoiId)-1);
            LatLng startingPoiPos = startingPoi.getPosition();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(startingPoiPos));
            CameraUpdateFactory.zoomTo(11);
        }
    }

}
