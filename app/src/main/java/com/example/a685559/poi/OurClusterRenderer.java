package com.example.a685559.poi;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import android.content.Context;

public class OurClusterRenderer extends DefaultClusterRenderer<InterestPoint> {

    public OurClusterRenderer(Context context, GoogleMap map, ClusterManager clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onClusterItemRendered(InterestPoint clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }

    @Override
    protected void onBeforeClusterItemRendered(InterestPoint poi, MarkerOptions markerOptions) {
        markerOptions.title(poi.getTitle());
    }

    @Override
    protected void onBeforeClusterRendered(Cluster cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        //If markers near are > 4 a cluster will be created.
        return cluster.getSize() > 4;
    }

    @Override
    protected int getBucket(Cluster cluster) {
        // show exact number of items in cluster's bubble
        return cluster.getSize();
    }
}

