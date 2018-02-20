package com.example.a685559.poi;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment {

    private GoogleMap mMap;

    MapView mMapView;

    ArrayList<InterestPoint> poiList;

    ClusterManager clusterManager;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.poiList = (ArrayList<InterestPoint>) bundle.getSerializable("POILIST");
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
                //onPointDetail(id);
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
            /*InterestPoint poi2 = poi;
            poi2.setTitle(poi.getId() + " " + poi.getTitle());*/
            clusterManager.addItem(poi);
        }
        clusterManager.cluster();
    }

    public void setStartPosition() {
        LatLng iniPos = new LatLng(41.391926, 2.165208);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(iniPos));
        mMap.setMinZoomPreference(12);
    }

}
