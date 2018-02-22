package com.example.a685559.poi.fragments;


import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.R;
import com.example.a685559.poi.listeners.PointOfInterestListener;
import com.example.a685559.poi.response.InterestPointController;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnDetailFragment extends Fragment implements PointOfInterestListener {

    TextView title, address, transport, email, telephone, description;

    TextView subTitleTransport;

    RelativeLayout loadingPanel;

    String id;

    private SwipeRefreshLayout swipeRefreshLayout;

    public OnDetailFragment() {
        // Required empty public constructor
    }

    public void fetchPoi() {
        InterestPointController interestPointController = new InterestPointController(this);
        interestPointController.start(this.id);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.id = (String) bundle.getSerializable("ID");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View poiView = inflater.inflate(R.layout.fragment_on_detail, container, false);

        title = poiView.findViewById(R.id.detailPoiTitle);
        address = poiView.findViewById(R.id.detailPoiAddress);
        subTitleTransport = poiView.findViewById(R.id.transportSubTitle);
        transport = poiView.findViewById(R.id.detailPoiTransport);
        email = poiView.findViewById(R.id.detailPoiEmail);
        telephone = poiView.findViewById(R.id.detailPoiTelephone);
        description = poiView.findViewById(R.id.detailPoiDescr);

        swipeRefreshLayout = poiView.findViewById(R.id.swipeRefresh);
        loadingPanel = poiView.findViewById(R.id.loadingPanel);

        return poiView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        fetchPoi();
                    }
                }
        );
        fetchPoi();
    }

    @Override
    public void onPointDetailSuccess(InterestPoint poi) {
        swipeRefreshLayout.setRefreshing(false);
        loadingPanel.setVisibility(View.GONE);

        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title.setText(poi.getTitle());
        address.setText(poi.getAddress());

        if (poi.getTransport().equals("null")) {
            subTitleTransport.setVisibility(View.GONE);
            transport.setVisibility(View.GONE);
        } else {
            transport.setText(poi.getTransport());
        }

        if (poi.getEmail().equals("null") || poi.getEmail().equals("undefined")) {
            email.setVisibility(View.GONE);
        } else {
            email.setText(poi.getEmail());
        }

        if (poi.getPhone().equals("null") || poi.getPhone().equals("undefined")) {
            telephone.setVisibility(View.GONE);
        } else {
            telephone.setText(poi.getPhone());
        }

        description.setText(poi.getDescription());
    }

    @Override
    public void onError() {

    }
}
