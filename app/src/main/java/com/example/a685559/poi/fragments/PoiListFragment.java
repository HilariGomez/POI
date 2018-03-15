package com.example.a685559.poi.fragments;


import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.R;
import com.example.a685559.poi.adapters.Adapter;
import com.example.a685559.poi.listeners.OnPoiSelectedListener;
import com.example.a685559.poi.listeners.PoiListListener;
import com.example.a685559.poi.response.InterestPointList;
import com.example.a685559.poi.response.ListController;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class PoiListFragment extends Fragment implements PoiListListener {

    private RecyclerView recyclerView;

    private Adapter mAdapter;

    private OnPoiSelectedListener onPoiSelectedListener;

    private ArrayList<InterestPoint> poiList;

    private SwipeRefreshLayout swipeRefreshLayout;

    RelativeLayout loadingPanel;

    String poiSelectedId = "1";

    public PoiListFragment() {
        // Required empty public constructor
    }

    public ArrayList<InterestPoint> getPoiList() {
        return poiList;
    }

    public String getPoiSelectedId() {
        return poiSelectedId;
    }

    public void fetchList() {
        ListController listController = new ListController(this);
        listController.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onPoiSelectedListener = (OnPoiSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.poiList = (ArrayList<InterestPoint>) bundle.getSerializable("POILIST");
            poiSelectedId = bundle.getString("SELECTEDPOI");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View poiListView = inflater.inflate(R.layout.fragment_poi_list, container, false);
        swipeRefreshLayout = poiListView.findViewById(R.id.swipeRefresh);
        loadingPanel = poiListView.findViewById(R.id.loadingPanel);

        recyclerView = poiListView.findViewById(R.id.recycler_view);
        mAdapter = new Adapter(this, poiSelectedId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if(!poiSelectedId.equals("none")) {
            recyclerView.scrollToPosition(Integer.valueOf(poiSelectedId)-1);
        }

        return poiListView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        fetchList();
                    }
                }
        );
        if (poiList != null && poiList.size() > 0) {
            inflateList(poiList);
        } else if (savedInstanceState != null) {
            InterestPointList pointListRecovered = (InterestPointList) savedInstanceState.getSerializable("POILIST");
            if (pointListRecovered != null) {
                inflateList(pointListRecovered.getList());
            } else {
                fetchList();
            }
        } else {
            fetchList();
        }
    }

    public void savePoiList(Bundle instanceStateToSave) {
        if (poiList != null) {
            InterestPointList interestPointList = new InterestPointList();
            interestPointList.setList(poiList);
            instanceStateToSave.putSerializable("POILIST", interestPointList);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle instanceStateToSave) {
        super.onSaveInstanceState(instanceStateToSave);
        savePoiList(instanceStateToSave);
    }

    public void inflateList(ArrayList<InterestPoint> poiList) {
        loadingPanel.setVisibility(View.GONE);
        this.poiList = poiList;
        mAdapter.addItems(poiList);
    }

    @Override
    public void onPointListSuccess(ArrayList<InterestPoint> poiList) {
        swipeRefreshLayout.setRefreshing(false);
        inflateList(poiList);
    }

    @Override
    public void onPointDetail(String id) {
        poiSelectedId = id;
        onPoiSelectedListener.onPoiSelected(id);
    }

    @Override
    public void onError() {

    }
}
