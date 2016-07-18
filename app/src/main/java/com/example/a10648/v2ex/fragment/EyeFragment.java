package com.example.a10648.v2ex.fragment;

import android.graphics.Color;
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


public class EyeFragment extends Fragment  {


    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
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
        pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pager_title);

        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.tab_title_bg));
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTextSpacing(40);
        pagerTabStrip.setDrawFullUnderline(true);



        titles.add("最热主题");
        titles.add("最新主题");
        titles.add("用户主页");

        HotFragment hotFragment = new HotFragment(); //最热主题的fragment
        LatestFragment latestFragment = new LatestFragment();
        UserPageFragment userPageFragment = new UserPageFragment();

        fragments.add(latestFragment);
        fragments.add(hotFragment);
        fragments.add(userPageFragment);


        MyFragPaAdapter myFragPaAdapter = new MyFragPaAdapter(getFragmentManager(), fragments, titles);
        viewPager.setAdapter(myFragPaAdapter);

        return view;
    }


}
