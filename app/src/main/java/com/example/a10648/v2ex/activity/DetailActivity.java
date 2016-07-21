package com.example.a10648.v2ex.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.jsoup.DetailJsoup;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    TextView contentDetails;

    DetailJsoup detailJsoupInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup_item2_layout);
        contentDetails = (TextView)findViewById(R.id.content_details);

        Intent intent = getIntent();
        String url_con = intent.getStringExtra("url_con");
        Log.d(TAG,url_con);
        detailJsoupInstance = new DetailJsoup(url_con);

        new getContentAsyncTask().execute();







    }


    public class getContentAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            detailJsoupInstance.Jconn();
            return detailJsoupInstance.getContentDetails();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, s);
            contentDetails.setText(s); //这个setText 方法必须在onPostExecute中，否则显示不出来
        }
    }
}
