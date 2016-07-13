package com.example.a10648.v2ex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {



    public LatestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View latest_view = LayoutInflater.from(getActivity()).inflate(R.layout.eye_latest_layout, container, false);
        RecyclerView recycleView = (RecyclerView) latest_view.findViewById(R.id.recycle_view);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        List<String> Nolist = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Nolist.add(i + "");
        }
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(new MyRecycleViewAdapter(Nolist, getActivity()));
        ButterKnife.bind(this, latest_view);
        return latest_view;
    }


}

