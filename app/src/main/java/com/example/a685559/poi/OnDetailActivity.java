package com.example.a685559.poi;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class OnDetailActivity extends AppCompatActivity implements InterestPointController.PointOfInterestListener {

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

    public void fetchPoi() {
        Bundle b = getIntent().getExtras();
        String id = (String) b.get("ID");
        InterestPointController interestPointController = new InterestPointController(this);
        interestPointController.start(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_item_back) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointDetailSuccess(InterestPoint poi) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        title = (TextView) findViewById(R.id.detailPoiTitle);
        address = (TextView) findViewById(R.id.detailPoiAddress);
        transport = (TextView) findViewById(R.id.detailPoiTransport);
        email = (TextView) findViewById(R.id.detailPoiEmail);
        description = (TextView) findViewById(R.id.detailPoiDescr);

        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title.setText(poi.getTitle());
        address.setText(poi.getAddress());
        transport.setText(poi.getTransport());
        email.setText(poi.getEmail());
        //description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        description.setText(poi.getDescription());
    }

    @Override
    public void onError() {

    }
}
