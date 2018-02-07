package com.example.a685559.poi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

public class OnDetailActivity extends Activity implements InterestPointController.PointOfInterestListener {

    TextView title, address, transport, email, description;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_detail);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        fetchPoi();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        fetchPoi();
    }

    public void fetchPoi(){
        Bundle b = getIntent().getExtras();
        InterestPoint poi = (InterestPoint) b.get("POI");
        InterestPointController interestPointController = new InterestPointController(this);
        interestPointController.start(poi.getId());
    }

    @Override
    public void onPointDetailSuccess(InterestPoint poi) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        title = (TextView) findViewById(R.id.detailPoiTitle);
        address = (TextView) findViewById(R.id.detailPoiAddress);
        transport = (TextView) findViewById(R.id.detailPoiTransport);
        email = (TextView) findViewById(R.id.detailPoiEmail);
        description = (TextView) findViewById(R.id.detailPoiDescr);

        title.setText(poi.getTitle());
        address.setText(poi.getAddress());
        transport.setText(poi.getTransport());
        email.setText(poi.getEmail());
        description.setText(poi.getDescription());
    }

    @Override
    public void onError() {

    }
}
