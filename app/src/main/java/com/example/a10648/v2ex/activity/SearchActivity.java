package com.example.a10648.v2ex.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.dao.MyDatabaseHelper;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    private MyDatabaseHelper dbHelper;


    private List<TopicModel> dbData = new ArrayList<>();


    /**
     * 搜索结果列表view
     */
    private RecyclerView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;
    /**
     * 搜索结果的数据
     */
    private List<TopicModel> resultData;

    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private MyRecyclerViewAdapter2 resultAdapter;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 12;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();  //初始化数据
        initViews();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 初始化视图
     */
    private void initViews() {
        lvResults = (RecyclerView) findViewById(R.id.main_lv_search_results);
        lvResults.setItemAnimator(new DefaultItemAnimator());
        lvResults.setLayoutManager(new LinearLayoutManager(this));
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

//        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(SearchActivity.this, position + "", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        dbHelper = new MyDatabaseHelper(this, "Topics.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //查询Topic.db中所有的数据
        Cursor cursor = db.query("Topic", null, null, null, null,  null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                long created = cursor.getLong(cursor.getColumnIndex("created"));
                int replies = cursor.getInt(cursor.getColumnIndex("replies"));
                String nodename = cursor.getString(cursor.getColumnIndex("nodename"));
                TopicModel model = new TopicModel(title, url, content, avatar , username, created, replies, nodename);
                dbData.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        dbHelper = new MyDatabaseHelper(this, "Topics.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //查询Topic.db中所有的数据
        Cursor cursor = db.query("Topic", null, null, null, null,  null, null);
        hintData = new ArrayList<>(hintSize);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));

               hintData.add(title);
            } while (cursor.moveToNext());
        }
        cursor.close();

//        hintData.add("让互联网更安全 —— 配置好自己的 HTTPS");
//        hintData.add("想做一个量化 app，以数据的方式来量化工作，生活的各个方面");
//        hintData.add("大家有没有什么好的点子或者创业项目");
//        hintData.add("Apple");
//        hintData.add("微云为什么干不过百度云，甚至是360云？");
//        hintData.add("云计算");
//        hintData.add("Ubuntu 下 Sublime 无法输入中文，一站式解决～～");
//        hintData.add("node.js");
//        hintData.add("为什么有人会接受新工作的工资低于上一家？");
//        hintData.add("分享一个 HTC 的坑");
//        hintData.add("上高三和做码农哪个累？");
//        hintData.add("买域名有必要买隐私保护么？");
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
//                    Log.d("SearchActivity", resultData.get(0).toString());
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new MyRecyclerViewAdapter2(resultData, this);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
        resultAdapter.setmOnItemClickListener(new MyRecyclerViewAdapter2.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, TopicModel data) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(data.url));
//                startActivity(intent);

                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("url_con", data.getUrl());
                intent.putExtra("avatar", data.getAvatar());
                intent.putExtra("node_name", data.getNodename());
                intent.putExtra("name", data.getUsername());
                intent.putExtra("create", data.getCreated());
                intent.putExtra("replies", data.getReplies());
                startActivity(intent);
            }
        });
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();


    }

}
