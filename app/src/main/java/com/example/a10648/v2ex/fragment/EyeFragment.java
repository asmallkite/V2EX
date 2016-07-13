package com.example.a10648.v2ex.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyFragPaAdapter;

import java.util.ArrayList;
import java.util.List;


public class EyeFragment extends Fragment {


    private ViewPager viewPager;
    private PagerTabStrip tab_title;
    private List<String> titles = new ArrayList<>();//页卡标题集合
    private List<Fragment> fragments = new ArrayList<>(); //fragment集合



//    private OnFragmentInteractionListener mListener;

    public EyeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eye, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        tab_title = (PagerTabStrip) view.findViewById(R.id.pager_title);

        tab_title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tab_title.setTabIndicatorColor(Color.BLUE);
        tab_title.setDrawFullUnderline(true);

        titles.add("最新主题");
        titles.add("用户主页");

        LatestFragment latestFragment = new LatestFragment();
        Fragment fragment2 = new Fragment();

        fragments.add(latestFragment);
        fragments.add(fragment2);

        MyFragPaAdapter myFragPaAdapter = new MyFragPaAdapter(getFragmentManager(), fragments, titles);
        viewPager.setAdapter(myFragPaAdapter);

        return view;
    }


}
