package com.example.a10648.v2ex.activity;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.NodeAdapter;
import com.example.a10648.v2ex.model.Node;
import com.example.a10648.v2ex.net.HttpConnect;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AllNodeActivity extends AppCompatActivity {
    public static final String TAG = "AllNodeActivity";

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<Node> nodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_node2);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.all_node_refresh);
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
                    }
                }        , 4000   );

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.all_node_rv);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        new MyTask().execute();

    }
    public class MyTask  extends AsyncTask<Void, Integer, List<Node>> {

        @Override
        protected List<Node> doInBackground(Void... params) {
            String base_url = "https://www.v2ex.com/api/nodes/show.json?id=";

            String response = null;
            JSONObject jsonObject = null;
            String url = null;
            String topics= null;
            String header= null;
            String title= null;
            String avatar= null;
           for (int i = 1; i < 50; i ++) {
               try {
                   response = HttpConnect.sendRequestWithHttpURLConnection(base_url + i);
                  jsonObject = new JSONObject(response);
                   avatar = jsonObject.getString("avatar_large");
                   if (avatar.startsWith("//")) {
                       avatar = "http:" + avatar;
                   }
                   url = jsonObject.getString("url");
                   topics = jsonObject.getString("topics");
                   header = jsonObject.getString("title_alternative");
                   title = jsonObject.getString("title");
               }catch (Exception e) {
                   e.printStackTrace();
               }

               nodeList.add(new Node(url,avatar,title,header,topics));
            }
            return nodeList;
        }

        @Override
        protected void onPostExecute(List<Node> nodes) {
            super.onPostExecute(nodes);
            recyclerView.setAdapter(new NodeAdapter(nodes, AllNodeActivity.this));
        }
    }
}
