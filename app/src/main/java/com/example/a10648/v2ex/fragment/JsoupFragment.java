package com.example.a10648.v2ex.fragment;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.dao.MyDatabaseHelper;
import com.example.a10648.v2ex.jsoup.MyJsoup;
import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.model.TopicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬数据的通用fragment
 */
public class JsoupFragment extends Fragment {

    public static final String TAG = "JsoupFragment";
    //参数传递的键值
    private static final String ARG = "arg";


    RecyclerView j_recycle_view;
    SwipeRefreshLayout swipeRefreshLayout;
    JRecycleViewAdapter jRecycleViewAdapter;

    String tab_url;

    List<JtopicModel> jtopicModels = new ArrayList<>();

    MyJsoup myJsoup;

    //数据库相关实例
    private MyDatabaseHelper dbHelper; //数据库对象
    SQLiteDatabase db;


    public JsoupFragment() {
        // Required empty public constructor
    }

    /**
     * 为了方便EyeFragment 与 JsoupFragment的参数传递，用newInstance 的方法去传递参数
     * @param arg
     * @return
     */
    public static Fragment newInstance (String arg) {
        JsoupFragment jsoupFragment = new JsoupFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG, arg);
        jsoupFragment.setArguments(bundle);
        return jsoupFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View j_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_jsoup, container, false);

        //创建SQLiteOpenHelper实例
        dbHelper = new MyDatabaseHelper(getActivity(), "Topics.db", null, 1);
        db = dbHelper.getWritableDatabase();


        swipeRefreshLayout = (SwipeRefreshLayout) j_view.findViewById(R.id.j_swipe_refresh);
        j_recycle_view = (RecyclerView)j_view.findViewById(R.id.j_recycle_view);

        initSwipeRefresh();
        initRecyclerView();
        return j_view;
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
                //刷新执行网络获取操作
                new JsoupTask().execute();

                Cursor cursor = db.query(getArguments().getString(ARG), null, null, null, null,  null, null);
                if (cursor.moveToFirst()) {
                    String title_old = cursor.getString(cursor.getColumnIndex("Jtitle"));
                    Log.d(TAG, "old \n " + title_old + "new \n" + jtopicModels.get(0).getJtitle() + "\n");
                    if (jtopicModels.get(0).getJtitle().equals(title_old)) {

                        Toast.makeText(getActivity(), "已经是最新数据哦", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        jRecycleViewAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "有更新", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                cursor.close();

            }
        });

    }


    public void initRecyclerView () {
        j_recycle_view.setItemAnimator(new DefaultItemAnimator());
        j_recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));


        if (db.query(getArguments().getString(ARG), null, null, null, null,  null, null).moveToFirst()) {
            getDbData();
        } else if (MyApplication.isNetWorkConnected > 0) {
            new JsoupTask().execute();
        } else {
            Toast.makeText(getActivity(), "啊哦， 网络开小差了", Toast.LENGTH_SHORT).show();
        }

        jRecycleViewAdapter = new JRecycleViewAdapter(jtopicModels, getActivity());
        j_recycle_view.setAdapter(jRecycleViewAdapter);
        jRecycleViewAdapter.notifyDataSetChanged();
        jRecycleViewAdapter.setmOnItemClickListener(new JRecycleViewAdapter.JOnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, JtopicModel data) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(data.Jurl));
//                startActivity(intent);
//                Intent intent = new Intent(getActivity(), DetailActivity.class);
//                startActivity(intent);
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


    }


    public class JsoupTask extends AsyncTask<JtopicModel, Integer, List<JtopicModel>> {

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

    /**
     * 获取db 数据
     */
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
    }
}
