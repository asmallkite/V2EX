package com.example.a10648.v2ex.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a10648.v2ex.MyApplication;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.activity.DetailActivity;
import com.example.a10648.v2ex.adapter.JRecycleViewAdapter;
import com.example.a10648.v2ex.dao.MyDatabaseHelper;
import com.example.a10648.v2ex.jsoup.MyJsoup;
import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬数据的通用fragment
 */
public class JsoupFragment extends Fragment {

//    public static final String TAG = "JsoupFragment";
    //参数传递的键值
    private static final String ARG = "arg";
    //将传过来的键值保存下
    String tab_url = null;
    //是否第一次加载
    boolean isFirstLoad = true;
    //是否第一次创建
    private boolean isFirstCreate = true;
    //当前fragment 是否可见
    boolean isVisible = false;
    //是否初始化了fragment view
    boolean isViewInit = false;

    //oncreateView 方法中的view 在初始化控件时引用
    View j_view;
    RecyclerView j_recycle_view;
    SwipeRefreshLayout swipeRefreshLayout;
    JRecycleViewAdapter jRecycleViewAdapter;
    private int firstVisibleItem;
    private int lastVisibleItem;
    //在获取recycleview 第一个 和 最后一个 item位置时调用
    private LinearLayoutManager linearLayoutManager;


    List<JtopicModel> jtopicModels = new ArrayList<>();

    MyJsoup myJsoup;

    //数据库相关实例
    private MyDatabaseHelper dbHelper; //数据库对象
    SQLiteDatabase db;


    Presenter presenter;

    public JsoupFragment() {
        // Required empty public constructor
    }

    /**
     * 为了方便EyeFragment 与 JsoupFragment的参数传递，用newInstance 的方法去传递参数
     * @param arg 传的参数
     * @return fragment
     */
    public static Fragment newInstance (String arg) {
        JsoupFragment jsoupFragment = new JsoupFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG, arg);
        jsoupFragment.setArguments(bundle);
        return jsoupFragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab_url = getArguments().getString(ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        j_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_jsoup, container, false);

        initSwipeRefresh();
        initRecyclerView();
        isViewInit = true;

        //创建SQLiteOpenHelper实例
        dbHelper = new MyDatabaseHelper(getActivity(), "Topics.db", null, 1);
        db = dbHelper.getWritableDatabase();

        lazyLoadFragment();

        return j_view;
    }

    /**
     * 懒加载
     * 一个Activity里面可能会以viewpager（或其他容器）与多个Fragment来组合使用，而如果每
     * 个fragment都需要去加载数据，或从本地加载，或从网络加载，那么在这个activity刚创建的
     * 时候就变成需要初始化大量资源。这样的结果，我们当然不会满意
     * 。那么，能不能做到当切换到这个fragment的时候，它才去初始化呢？
     * @param isVisibleToUser 默认为ture
     */

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoadFragment();
        } else {
            isVisible = false;
        }
    }

    private void lazyLoadFragment() {

        if ( !isVisible || !isFirstLoad || !isViewInit) {
            return;
        }
        initData();
        isFirstLoad = false;
    }


    public void initSwipeRefresh () {
        swipeRefreshLayout = (SwipeRefreshLayout)j_view.findViewById(R.id.j_swipe_refresh);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "已经是最新数据哦", Toast.LENGTH_SHORT).show();
                    }
                }        , 4000   );

            }
        });

    }


    public void initRecyclerView () {
        j_recycle_view = (RecyclerView)j_view.findViewById(R.id.j_recycle_view);
        j_recycle_view.setItemAnimator(new DefaultItemAnimator());
        j_recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));
        linearLayoutManager = new LinearLayoutManager(getContext());
        jRecycleViewAdapter = new JRecycleViewAdapter(jtopicModels, getActivity());
        j_recycle_view.setAdapter(jRecycleViewAdapter);
        /**
         * 点击事件，进入二级界面
         */
        jRecycleViewAdapter.setmOnItemClickListener(new JRecycleViewAdapter.JOnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, JtopicModel data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("url_con", data.getJurl());
                intent.putExtra("avatar", data.getJavatar());
                intent.putExtra("node_name", data.getJnodename());
                intent.putExtra("name", data.getJusername());
                intent.putExtra("create", data.getJcreated());
                intent.putExtra("replies", data.getJreplies());
                startActivity(intent);
            }
        });
        /**
         * 滑动事件
         */
        j_recycle_view.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE )
                    if (firstVisibleItem == 0 ){
                        swipeRefreshLayout.setRefreshing(true);
                        presenter.refresh(tab_url);// no point 空指针异常
                    } else if (lastVisibleItem + 1 == jRecycleViewAdapter.getItemCount()) {
                        presenter.load(tab_url, jtopicModels.size());

                        Toast.makeText(getContext(), "正在加载，请稍后", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    /**
     * 初始化数据，并调用Presenter
     */
    public void initData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        //7/25将 网络判断删除
            presenter = new Presenter(this);
            presenter.refresh(tab_url);


    }

    public void onRefresh (List<JtopicModel> list_model) {
        if (list_model == null) {
            Toast.makeText(getContext(), "网络不可用-刷新失败", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        if (isFirstCreate && list_model.size() < 5) {
            presenter.load(getArguments().getString(ARG), jtopicModels.size());
            isFirstCreate = false;
            //加载完数据取消加载进度条
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        Toast.makeText(MyApplication.getContext(), list_model.size() == 0 ?
        "已经是最新数据哦" : ("客官，您的菜，请慢用"), Toast.LENGTH_SHORT).show();
        jtopicModels.addAll(0, list_model);
        jRecycleViewAdapter.notifyDataSetChanged();
        saveToDb();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void saveToDb() {
        if ( !db.query(getArguments().getString(ARG), null, null, null, null,  null, null).moveToFirst()) {
            //执行数据库添加操作
            for (int i = 0; i < jtopicModels.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("Javatar", jtopicModels.get(i).getJavatar());
                values.put("Jurl", jtopicModels.get(i).getJurl());
                values.put("Jtitle", jtopicModels.get(i).getJtitle());
                values.put("Jnodename", jtopicModels.get(i).getJnodename());
                values.put("Jcreated", jtopicModels.get(i).getJcreated());
                values.put("Jusername", jtopicModels.get(i).getJusername());
                values.put("Jreplies", jtopicModels.get(i).getJreplies());

                db.insert(getArguments().getString(ARG), null, values);
                values.clear();
            }
        }
    }

    public void onLoad (List<JtopicModel> jtopicModel2) {
        if (jtopicModels == null) {
            return;
        }

        jtopicModels.addAll(jtopicModel2);
        jRecycleViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }













  /*  public class JsoupTask extends AsyncTask<JtopicModel, Integer, List<JtopicModel>> {

        @Override
        protected List<JtopicModel> doInBackground(JtopicModel... params) {
            //构造MyJsoup带参的函数
            myJsoup = new MyJsoup(getArguments().getString(ARG));

            myJsoup.Jconn();

            jtopicModels = myJsoup.getTopicModel();
            return jtopicModels;
        }

        @Override
        protected void onPostExecute(List<JtopicModel> jtopicModels) {
            super.onPostExecute(jtopicModels);

         if ( !db.query(getArguments().getString(ARG), null, null, null, null,  null, null).moveToFirst()) {
             //执行数据库添加操作
             for (int i = 0; i < jtopicModels.size(); i++) {
                 ContentValues values = new ContentValues();
                 values.put("Javatar", jtopicModels.get(i).getJavatar());
                 values.put("Jurl", jtopicModels.get(i).getJurl());
                 values.put("Jtitle", jtopicModels.get(i).getJtitle());
                 values.put("Jnodename", jtopicModels.get(i).getJnodename());
                 values.put("Jcreated", jtopicModels.get(i).getJcreated());
                 values.put("Jusername", jtopicModels.get(i).getJusername());
                 values.put("Jreplies", jtopicModels.get(i).getJreplies());

                 db.insert(getArguments().getString(ARG), null, values);
                 values.clear();
             }
         }
        }
    }

    *//**
     * 获取db 数据
     *//*
    private void getDbData() {
        //查询Topic.db中所有的数据
        Cursor cursor = db.query(getArguments().getString(ARG), null, null, null, null,  null, null);
        if (cursor.moveToFirst()) {
            do {
                String Javatar = cursor.getString(cursor.getColumnIndex("Javatar"));
                String Jurl = cursor.getString(cursor.getColumnIndex("Jurl"));
                String Jtitle = cursor.getString(cursor.getColumnIndex("Jtitle"));
                String Jnodename = cursor.getString(cursor.getColumnIndex("Jnodename"));
                String Jcreated = cursor.getString(cursor.getColumnIndex("Jcreated"));
                String Jusername = cursor.getString(cursor.getColumnIndex("Jusername"));
                String Jreplies = cursor.getString(cursor.getColumnIndex("Jreplies"));

                JtopicModel jtopicModel = new JtopicModel(Javatar, Jurl, Jtitle, Jnodename, Jcreated, Jusername, Jreplies);
                jtopicModels.add(jtopicModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }*/
}
