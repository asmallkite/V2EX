package com.example.a10648.v2ex.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.JcommentAdapter;
import com.example.a10648.v2ex.jsoup.DetailJsoup;
import com.example.a10648.v2ex.model.CommentModel;
import com.example.a10648.v2ex.widget.SelectorImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DetailActivity extends AppCompatActivity  {


    /*二级界面第二个cardView*/
    /*这块是文章正文 文字和图片，最多五张图片，文章里面不够五张，有几张显示几张*/
    TextView contentDetails;

    /*二级界面第一个cardView*/
    SelectorImageView avatar;
    TextView nodename;
    TextView name;
    TextView create;
    TextView replies;
    DetailJsoup detailJsoupInstance;

    /*二级界面第三个cardView*/
    RecyclerView comment_recycle_view;

    String url_con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jsoup_item2_layout);

        init(); //初始化，绑定控件
        display_card_1();

        url_con = getIntent().getStringExtra("url_con");
        detailJsoupInstance = new DetailJsoup(url_con);

        new getFinalContentAsyncTask().execute();
        new getCommentAsynctask().execute();


        //设置comment_recycle_view
        setCommentRecyclerView();

    }

    private void init() {
        contentDetails = (TextView) findViewById(R.id.content_details);
        avatar = (SelectorImageView) findViewById(R.id.avatar);
        nodename = (TextView) findViewById(R.id.node_name);
        name = (TextView) findViewById(R.id.name);
        create = (TextView) findViewById(R.id.create);
        replies = (TextView) findViewById(R.id.replies);
        comment_recycle_view = (RecyclerView) findViewById(R.id.comment_recycle_view);
    }

    private void display_card_1() {
        //显示二级界面图片
        ImageLoader.getInstance().loadImage(getIntent().getStringExtra("avatar"), new SimpleImageLoadingListener() {
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

    public void setCommentRecyclerView() {
        comment_recycle_view.setItemAnimator(new DefaultItemAnimator());
        comment_recycle_view.setLayoutManager(new LinearLayoutManager(this));

    }

        public class getFinalContentAsyncTask extends AsyncTask<Void, Integer, String> {


            @Override
            protected String doInBackground(Void... params) {
                detailJsoupInstance.Jconn();
                return detailJsoupInstance.get_final_content();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Spanned spanned = Html.fromHtml(s, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        LevelListDrawable d = new LevelListDrawable();
                        Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
                        d.addLevel(0, 0, empty);
                        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

                        new LoadImage().execute(source, d);

                        return d;
                    }
                }, null);
                contentDetails.setText(spanned);


            }
        }


        public class getCommentAsynctask extends AsyncTask<Void, Intent, List<CommentModel>> {
            @Override
            protected List<CommentModel> doInBackground(Void... params) {
                detailJsoupInstance.Jconn();
                return detailJsoupInstance.getCommentModel();
            }

            @Override
            protected void onPostExecute(List<CommentModel> commentModels) {
                super.onPostExecute(commentModels);
                if (commentModels.size() == 0) {
                    CommentModel a_model = new CommentModel("https://cdn.v2ex.co/gravatar/e34779a3ab6ef091c44f2fd4b1c8e60b?s=48&d=retro",
                            "Livid", "2分钟前", "来抢沙发哈", "3");
                    commentModels.add(a_model);
                }
                comment_recycle_view.setAdapter(new JcommentAdapter(commentModels, DetailActivity.this));
            }
        }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);

                CharSequence t = contentDetails.getText();
                contentDetails.setText(t);
            }
        }
    }


    }



