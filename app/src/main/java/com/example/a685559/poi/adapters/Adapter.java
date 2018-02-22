package com.example.a685559.poi.adapters;

import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.R;
import com.example.a685559.poi.listeners.PoiListListener;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<InterestPoint> POIlist;

    private PoiListListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, id, geo;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.poi_title);
            id = view.findViewById(R.id.poi_id);
            geo = view.findViewById(R.id.poi_geo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    InterestPoint selectedItem = POIlist.get(index);
                    listener.onPointDetail(selectedItem.getId());
                }
            });
        }
    }

    public Adapter(ArrayList<InterestPoint> POIlist, PoiListListener listener) {
        this.POIlist = POIlist;
        this.listener = listener;
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
