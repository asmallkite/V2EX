package com.example.a10648.v2ex.fragment;



import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a10648.v2ex.MyApplication;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.dao.MyDatabaseHelper;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.net.HttpConnect;
import com.example.a10648.v2ex.net.NetWorkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class LatestFragment extends Fragment {
//    public static final String TAG = "MainActivity";

    List<TopicModel> links = new ArrayList<>();


    RecyclerView recyclerView;


    public static final String LATEST_URL = "https://www.v2ex.com/api/topics/latest.json";


    //数据库相关实例
    private MyDatabaseHelper dbHelper; //数据库对象
    SQLiteDatabase db;


    public LatestFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View latest_view = LayoutInflater.from(getActivity()).inflate(R.layout.eye_latest_layout, container, false);

        //创建SQLiteOpenHelper实例
        dbHelper = new MyDatabaseHelper(getActivity(), "Topics.db", null, 1);
        db = dbHelper.getWritableDatabase();

        recyclerView = (RecyclerView) latest_view.findViewById(R.id.recycle_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new MyTask().execute();
        return latest_view;
    }



    /**
     * 异步执行网络操作
     * 后来加上了 网络下载 数据存储到Sqlite 中的代码
     */

    public class MyTask extends AsyncTask<TopicModel, Integer, List<TopicModel>> {


        @Override
        protected List<TopicModel> doInBackground(TopicModel... params) {
            String response = HttpConnect.sendRequestWithHttpURLConnection(LATEST_URL);
            praseJSONWithJSONObject(response);
            return links;
        }

        @Override
        protected void onPostExecute(List<TopicModel> models) {
            super.onPostExecute(models);
            //执行数据库添加操作
            for (int i = 0; i < models.size(); i++) {
//                db.execSQL(" insert into Topic ( title , url , content , avatar , " +
//                        "  username , created , replies , nodename ) " +
//                        " values ( ? ， ? , ? , ? , ? ，? , ? , ? ) ", new Object[] { models.get(i).getTitle(), models.get(i).getUrl(),
//                        models.get(i).getContent(), models.get(i).getAvatar(), models.get(i).getUsername(),
//                        models.get(i).getCreated(), models.get(i).getReplies(), models.get(i).getNodename()});

                ContentValues values = new ContentValues();
                values.put("title", models.get(i).getTitle());
                values.put("url", models.get(i).getUrl());
                values.put("content", models.get(i).getContent());
                values.put("avatar", models.get(i).getAvatar());

                values.put("username", models.get(i).getUsername());
                values.put("created", models.get(i).getCreated());
                values.put("replies", models.get(i).getReplies());
                values.put("nodename", models.get(i).getNodename());
                db.insert("Topic", null, values);
                values.clear();
//                Log.d("CREATEDB", "插入数据执行完毕");
            }

            MyRecyclerViewAdapter2 adapter2 = new MyRecyclerViewAdapter2(links, getContext());
            recyclerView.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
            adapter2.setmOnItemClickListener(new MyRecyclerViewAdapter2.OnRecycleViewItemClickListener() {
                @Override
                public void onItemClick(View view, TopicModel data) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(data.url));
                    startActivity(intent);

                }
            });
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




}

