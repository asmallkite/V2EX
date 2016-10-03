package com.example.a10648.v2ex.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyFragPaAdapter;
import com.example.a10648.v2ex.fragment.add.TFragment;
import com.example.a10648.v2ex.jsoup.MyJsoup;
import java.util.ArrayList;
import java.util.List;


public class EyeFragment extends Fragment  {

    ViewPager viewPager;
    TabLayout tabLayout;
    private List<String> titles = new ArrayList<>();//页卡标题集合
    private List<Fragment> fragments = new ArrayList<>(); //fragment集合


    public EyeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eye, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout)view.findViewById(R.id.sliding_tabs);

        titles.add("hot");
            /*根据id搜索用户主页*/
        titles.add("用户主页");
            /*根据API接口进行获取*/
        titles.add("最热主题");
        titles.add("最新主题");
            /*网络蜘蛛获取*/
        titles.add("创意");
        titles.add("技术");
        titles.add("好玩");
        titles.add("Apple");
        titles.add("工作");
        titles.add("交易");
        titles.add("城市");
        titles.add("问与答");
        titles.add("全部");
        titles.add("R2");


        TFragment tFragment = new TFragment();
        /**
         * 用户主页的fragment
         */
        UserPageFragment userPageFragment = new UserPageFragment();
        /**
         * 最新和最热的fragment，，用api获取
         */
        Fragment hotFragment = Hot_and_Latest_Fragment.newInstance(Hot_and_Latest_Fragment.Hot_URL);
        Fragment latestFragment = Hot_and_Latest_Fragment.newInstance(Hot_and_Latest_Fragment.LATEST_URL);

        /**
         * 网络蜘蛛获取
         */
        Fragment creative = JsoupFragment.newInstance(MyJsoup.CREATIVE);
        Fragment tech = JsoupFragment.newInstance(MyJsoup.TECH);
        Fragment play = JsoupFragment.newInstance(MyJsoup.PLAY);
        Fragment apple = JsoupFragment.newInstance(MyJsoup.APPLE);
        Fragment jobs = JsoupFragment.newInstance(MyJsoup.JOBS);
        Fragment deals = JsoupFragment.newInstance(MyJsoup.DEALS);
        Fragment city = JsoupFragment.newInstance(MyJsoup.CITY);
        Fragment qna = JsoupFragment.newInstance(MyJsoup.QNA);
        Fragment all = JsoupFragment.newInstance(MyJsoup.ALL);
        Fragment r2 = JsoupFragment.newInstance(MyJsoup.R2);



        fragments.add(tFragment);
        fragments.add(userPageFragment);

        fragments.add(hotFragment);
        fragments.add(latestFragment);

        fragments.add(creative);
        fragments.add(tech);
        fragments.add(play);
        fragments.add(apple);
        fragments.add(jobs);
        fragments.add(deals);
        fragments.add(city);
        fragments.add(qna);
        fragments.add(all);
        fragments.add(r2);



        MyFragPaAdapter myFragPaAdapter = new MyFragPaAdapter(getFragmentManager(), fragments, titles);
        viewPager.setAdapter(myFragPaAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }


}
