package com.example.a10648.v2ex.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    RecyclerView recyclerView;
    List<Node> nodeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_node2);
        recyclerView = (RecyclerView) findViewById(R.id.all_node_rv);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

           for (int i = 0; i < 50; i ++) {
               try {

                   response = HttpConnect.sendRequestWithHttpURLConnection(base_url + i);

                   jsonObject = new JSONObject(response);
                   avatar = jsonObject.getString("avatar_large");
                   if (avatar.startsWith("//")) {
                       avatar = "http:" + avatar;
                   }
                   url = jsonObject.getString("url");
                   topics = jsonObject.getString("topics");
                   header = jsonObject.getString("header");
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
