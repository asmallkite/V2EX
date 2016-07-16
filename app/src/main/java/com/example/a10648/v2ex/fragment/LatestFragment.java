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
import com.example.a10648.v2ex.net.SendRequestWithHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {
    public static final String TAG = "MainActivity";
    List<String> links = new ArrayList<>();
    RecyclerView recyclerView;



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


    public class MyTask extends AsyncTask<String, Integer, List<String>> {


        @Override
        protected List<String> doInBackground(String... params) {
            String response = SendRequestWithHttpURLConnection.sendRequestWithHttpURLConnection();
            praseJSONWithJSONObject(response);
            return links;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            MyRecyclerViewAdapter2 adapter2 = new MyRecyclerViewAdapter2(links, getContext());
            recyclerView.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
            adapter2.setmOnItemClickListener(new MyRecyclerViewAdapter2.OnRecycleViewItemClickListener() {
                @Override
                public void onItemClick(View view, String data) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(data));
                    startActivity(intent);
                }
            });


        }

    }



//    private static String sendRequestWithHttpURLConnection(){
//
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL("https://www.v2ex.com/api/topics/latest.json");
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(8000);
//            connection.setReadTimeout(8000);
//            Log.d(TAG, "ok");
//            InputStream in = connection.getInputStream();
//            Log.d(TAG, "ok2");
//
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null){
//                response.append(line);
//            }
//            return response.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }finally {
//            if (connection != null){
//                connection.disconnect();
//            }
//        }
//
//
//    }




    private void praseJSONWithJSONObject (String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i =  0; i < jsonArray.length(); i ++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String url = jsonObject.getString("url");
                Log.d(TAG, "result is that" + url);
                links.add(url);
                Log.d(TAG, links.toString());
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }




}

