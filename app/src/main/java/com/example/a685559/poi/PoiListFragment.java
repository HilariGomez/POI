package com.example.a685559.poi;


import com.example.a685559.poi.InterestPoint;
import com.example.a685559.poi.ListController;
import com.example.a685559.poi.R;
import com.example.a685559.poi.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoiListFragment extends Fragment implements ListController.PoiListListener {

    Context context;

    private RecyclerView recyclerView;

    private Adapter mAdapter;

    private ArrayList<InterestPoint> poiList;

    private ListController.PoiListListener listener;

    public PoiListFragment() {
        // Required empty public constructor
    }

    public ArrayList<InterestPoint> getPoiList(){
        return poiList;
    }

    public void fetchList() {
        ListController listController = new ListController(this);
        listController.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            this.poiList = (ArrayList<InterestPoint>) bundle.getSerializable("POILIST");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myList = inflater.inflate(R.layout.fragment_poi_list, container, false);
        recyclerView = (RecyclerView) myList.findViewById(R.id.recycler_view);
        return myList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchList();
    }


    @Override
    public void onPointListSuccess(ArrayList<InterestPoint> poiList) {
        this.poiList=poiList;
        mAdapter = new Adapter(poiList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPointDetail(String id) {

    }

    @Override
    public void onError() {

    }
}
