package com.example.a10648.v2ex.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.jsoup.DetailJsoup;
import com.example.a10648.v2ex.jsoup.MyJsoup;
import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.widget.SelectorImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    TextView contentDetails;
    SelectorImageView avatar;
    TextView nodename;
    TextView name;
    TextView create;
    TextView replies;
    DetailJsoup detailJsoupInstance;

    String url_con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup_item2_layout);

        init(); //初始化，绑定控件
        display_card_1();

        url_con = getIntent().getStringExtra("url_con");
        detailJsoupInstance = new DetailJsoup(url_con);

        new getContentAsyncTask().execute();
        
    }

    private void init() {
        contentDetails = (TextView)findViewById(R.id.content_details);
        avatar = (SelectorImageView) findViewById(R.id.avatar);
        nodename = (TextView) findViewById(R.id.node_name);
        name = (TextView) findViewById(R.id.name);
        create = (TextView) findViewById(R.id.create);
        replies = (TextView) findViewById(R.id.replies);
    }

    private void display_card_1() {
        //显示二级界面图片
        ImageLoader.getInstance().loadImage(getIntent().getStringExtra("avatar"),new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                avatar.setImageBitmap(loadedImage);
            }
        });
        nodename.setText(getIntent().getStringExtra("node_name"));
        name.setText(getIntent().getStringExtra("name"));
        create.setText(getIntent().getStringExtra("create"));
        replies.setText(getIntent().getStringExtra("replies"));


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
            contentDetails.setText(s); //这个setText 方法必须在onPostExecute中，否则显示不出来
        }
    }




}
