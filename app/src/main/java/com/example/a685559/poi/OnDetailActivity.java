package com.example.a685559.poi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OnDetailActivity extends Activity implements InterestPointController.PointOfInterestListener {

    TextView title, address, transport, email, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_detail);

        Bundle b = getIntent().getExtras();
        InterestPoint poi = (InterestPoint) b.get("POI");

        InterestPointController interestPointController = new InterestPointController(this);
        Integer index = Integer.parseInt(poi.getId()) + 1;
        interestPointController.start(index.toString());
    }

    @Override
    public void onPointDetailSuccess(InterestPoint poi) {
        title = (TextView) findViewById(R.id.detailPoiTitle);
        address = (TextView) findViewById(R.id.detailPoiAdress);
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
