package com.example.a685559.poi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements ListController.PoiListListener {

    private ArrayList<InterestPoint> POIlist;

    private InterestPointController.PointOfInterestListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, id, geo;

        public ImageButton info;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.poi_title);
            id = (TextView) view.findViewById(R.id.poi_id);
            geo = (TextView) view.findViewById(R.id.poi_geo);
            info = (ImageButton) view.findViewById(R.id.info);
        }
    }

    public Adapter(ArrayList<InterestPoint> POIlist) {
        this.POIlist = POIlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poi_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InterestPoint poi = POIlist.get(position);
        holder.title.setText(poi.getTitle());
        holder.id.setText(poi.getId());
        holder.geo.setText(poi.getGeoCoordinates());
    }

    @Override
    public int getItemCount() {
        return POIlist != null ? POIlist.size() : 0;
    }

    @Override
    public void onPointListSuccess(List<InterestPoint> poiList) {
    }

    @Override
    public void onError() {

    }
}
