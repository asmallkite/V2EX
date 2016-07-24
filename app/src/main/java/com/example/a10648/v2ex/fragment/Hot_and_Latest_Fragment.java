package com.example.a10648.v2ex.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a10648.v2ex.MyApplication;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.activity.DetailActivity;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.dao.MyDatabaseHelper;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.net.HttpConnect;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 7/23
 * 因为最新和最热 是由API获取的。所有区别爬虫获取的tab
 * 7月23日 我将HotFragment。java 和 latestFragment.java 合并在一起
 * 二者只是api和数据表名不一样
 *
 * 暂时没有完成
 */
public class Hot_and_Latest_Fragment extends Fragment {

    public static final String LATEST_URL = "https://www.v2ex.com/api/topics/latest.json";
    public static final String Hot_URL ="https://www.v2ex.com/api/topics/hot.json";

    //参数传递的键值
    private static final String ARG = "arg";
    String arg;

    List<TopicModel> links = new ArrayList<>();



    //数据库相关实例
    private MyDatabaseHelper dbHelper; //数据库对象
    SQLiteDatabase db;
    String table_name ;

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
     * @param arg api值， 其键为ARG，值为最新和最热话题的api 此处为完整的API
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arg = getArguments().getString(ARG);
        if (arg.equals(LATEST_URL)) {
                table_name = "Topic";
        } else {
            table_name = "Topic2";
        }
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


        initSwipeRefresh();
        initRecyclerView();


        return hot_and_latest_view;
    }

    public void initSwipeRefresh () {
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
                        Toast.makeText(getActivity(), "已经是最新数据哦", Toast.LENGTH_SHORT).show();
                    }
                }        , 4000   );

            }
        });

    }

    public void initRecyclerView() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (db.query(table_name, null, null, null, null,  null, null).moveToFirst()) {
            getDbData();
        } else if (MyApplication.isNetWorkConnected == 0) {
            Toast.makeText(getActivity(), "啊哦， 网络开小差了", Toast.LENGTH_SHORT).show();
        } else {
            new MyTask().execute();
        }

        MyRecyclerViewAdapter2 adapter2 = new MyRecyclerViewAdapter2(links, getContext());
        recyclerView.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        adapter2.setmOnItemClickListener(new MyRecyclerViewAdapter2.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, TopicModel data) {
                /*以下三行点击item 跳转到浏览器中打开*/
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(data.url));
//                startActivity(intent);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
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
     * 异步执行网络操作
     * 后来加上了 网络下载 数据存储到Sqlite 中的代码
     */

    public class MyTask extends AsyncTask<TopicModel, Integer, List<TopicModel>> {


        @Override
        protected List<TopicModel> doInBackground(TopicModel... params) {
            String response = HttpConnect.sendRequestWithHttpURLConnection(arg);
            praseJSONWithJSONObject(response);
            return links;
        }

        @Override
        protected void onPostExecute(List<TopicModel> models) {
            super.onPostExecute(models);
            //执行数据库添加操作
            for (int i = 0; i < models.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("title", models.get(i).getTitle());
                values.put("url", models.get(i).getUrl());
                values.put("content", models.get(i).getContent());
                values.put("avatar", models.get(i).getAvatar());

                values.put("username", models.get(i).getUsername());
                values.put("created", models.get(i).getCreated());
                values.put("replies", models.get(i).getReplies());
                values.put("nodename", models.get(i).getNodename());
                db.insert(table_name, null, values);   //插入数据表哦
                values.clear();
            }
        }
    }

    /**
     * 对HttpConnect 中的sendRequestWithHttpURLConnection 返回的response 进行解析
     * 把解析得到的topicModel 添加到List 中
     *
     * @param jsonData 返回的response
     */

    private void praseJSONWithJSONObject(String jsonData) {
        try {

            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String url = jsonObject.getString("url");
                JSONObject jsonObject1 = jsonObject.getJSONObject("member");
                String avatar = jsonObject1.getString("avatar_large");
                if (avatar.startsWith("//")) {
                    avatar = "http:" + avatar;
                }
                String username = jsonObject1.getString("username");
                long created = jsonObject.getLong("created");
                int replies = jsonObject.getInt("replies");
                JSONObject jsonObject2 = jsonObject.getJSONObject("node");
                String node_name = jsonObject2.getString("name");

                TopicModel topicModel = new TopicModel(title, url, content, avatar, username, created, replies, node_name);

                links.add(topicModel);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
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
                links.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

}
