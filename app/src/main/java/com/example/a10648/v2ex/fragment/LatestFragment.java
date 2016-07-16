package com.example.a10648.v2ex.fragment;



import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.net.HttpConnect;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class LatestFragment extends Fragment {
    public static final String TAG = "MainActivity";
    List<TopicModel> links = new ArrayList<>();
    RecyclerView recyclerView;
    public static final String LATEST_URL ="https://www.v2ex.com/api/topics/latest.json";



    public LatestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View latest_view = LayoutInflater.from(getActivity()).inflate(R.layout.eye_latest_layout, container, false);
        recyclerView = (RecyclerView) latest_view.findViewById(R.id.recycle_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new MyTask().execute();
        return latest_view;
    }

    /**
     * 异步执行网络操作
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






    private void praseJSONWithJSONObject (String jsonData) {
        try {

            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i =  0; i < jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String url = jsonObject.getString("url");
                JSONObject jsonObject1 = jsonObject.getJSONObject("member");
                String avatar = jsonObject1.getString("avatar_large");
                if(avatar.startsWith("//")){
                    avatar = "http:" + avatar;
                }
                String username = jsonObject1.getString("username");
                TopicModel topicModel = new TopicModel(title, url, content, avatar, username);

                links.add(topicModel);
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }


}

