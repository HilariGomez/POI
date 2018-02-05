package com.example.a685559.poi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class POIAdapter extends RecyclerView.Adapter<POIAdapter.MyViewHolder> {

    private ArrayList<InterestPoint> POIlist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, id, geo;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.poi_title);
            id = (TextView) view.findViewById(R.id.poi_id);
            geo = (TextView) view.findViewById(R.id.poi_geo);

        }
    }

    public POIAdapter(ArrayList<InterestPoint> POIlist) {
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
}
