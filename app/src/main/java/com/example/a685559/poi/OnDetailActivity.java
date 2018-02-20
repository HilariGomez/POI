package com.example.a685559.poi;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class OnDetailActivity extends AppCompatActivity implements InterestPointController.PointOfInterestListener {

    TextView title, address, transport, email, telephone, description;

    TextView subTitleTransport;

    InterestPoint poi;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
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

    public void fetchPoi() {
        Bundle b = getIntent().getExtras();
        String id = (String) b.get("ID");
        InterestPointController interestPointController = new InterestPointController(this);
        interestPointController.start(id);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }


    @Override
    public void onPointDetailSuccess(InterestPoint poi) {
        swipeRefreshLayout.setRefreshing(false);
        this.poi = poi;
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        title = (TextView) findViewById(R.id.detailPoiTitle);
        address = (TextView) findViewById(R.id.detailPoiAddress);
        subTitleTransport = (TextView) findViewById(R.id.transportSubTitle);
        transport = (TextView) findViewById(R.id.detailPoiTransport);
        email = (TextView) findViewById(R.id.detailPoiEmail);
        telephone = (TextView) findViewById(R.id.detailPoiTelephone);
        description = (TextView) findViewById(R.id.detailPoiDescr);

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
