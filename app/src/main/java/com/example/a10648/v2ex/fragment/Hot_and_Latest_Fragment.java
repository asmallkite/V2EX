package com.example.a10648.v2ex.fragment;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.dao.MyDatabaseHelper;

/**
 * 7/23
 * 因为最新和最热 是由API获取的。所有区别爬虫获取的tab
 * 7月23日 我将HotFragment。java 和 LatestFragment.java 合并在一起
 * 二者只是api和数据表名不一样
 */
public class Hot_and_Latest_Fragment extends Fragment {

    //参数传递的键值
    private static final String ARG = "arg";


    //数据库相关实例
    private MyDatabaseHelper dbHelper; //数据库对象
    SQLiteDatabase db;

    //RecycleView and SwipeRefreshLayout
    RecyclerView recyclerView;
    MyRecyclerViewAdapter2 adapter2;
    SwipeRefreshLayout swipeRefreshLayout;



    /**
     * 空的构造函数，方便其他类 创建此实例。配合使用getInstance
     */
    public Hot_and_Latest_Fragment() {
        // Required empty public constructor
    }


    /**
     * 在EyeFragment 创建实例 调用
     * @param arg api值， 其键为ARG，值为最新和最热话题的api
     * @return
     */
    public static Fragment newInstance (String arg) {
        Hot_and_Latest_Fragment hot_and_latest_fragment = new Hot_and_Latest_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG, arg);
        hot_and_latest_fragment.setArguments(bundle);
        return hot_and_latest_fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hot_and_latest_view = inflater.inflate(R.layout.fragment_hot_and__latest_, container, false);

        //创建SQLiteOpenHelper实例
        dbHelper = new MyDatabaseHelper(getActivity(), "Topics.db", null, 1);
        db = dbHelper.getWritableDatabase();

        recyclerView = (RecyclerView) hot_and_latest_view.findViewById(R.id.recycle_view);
        swipeRefreshLayout = (SwipeRefreshLayout) hot_and_latest_view.findViewById(R.id.refresh);


        return hot_and_latest_view;
    }

}
