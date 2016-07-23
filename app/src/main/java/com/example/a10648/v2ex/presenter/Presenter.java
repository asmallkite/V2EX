package com.example.a10648.v2ex.presenter;

import android.os.AsyncTask;

import com.example.a10648.v2ex.MyApplication;
import com.example.a10648.v2ex.fragment.JsoupFragment;
import com.example.a10648.v2ex.jsoup.MyJsoup;
import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.model.TopicModel;

import java.util.List;

/**
 * Created by 10648 on 2016/7/23 0023.
 */
public class Presenter {
    JsoupFragment jsoupFragment;

    public Presenter(JsoupFragment jsoupFragment) {
        this.jsoupFragment = jsoupFragment;
    }

    public void refresh (String tab_url) {
        if (MyApplication.isNetWorkConnected > 0) {
            new RefreshTask().execute(tab_url);
        }
    }



    private class RefreshTask extends AsyncTask<String, Integer, List<JtopicModel>> {

        @Override
        protected List<JtopicModel> doInBackground(String... params) {
            MyJsoup myJsoup = new MyJsoup(params[0]);
            myJsoup.Jconn();
            return myJsoup.getTopicModel();
        }

        @Override
        protected void onPostExecute(List<JtopicModel> list_model) {
            super.onPostExecute(list_model);
            jsoupFragment.onRefresh(list_model);
        }
    }


    public void load(String key, int currentItemCount) {
        new LoadTask(currentItemCount).execute(key);
    }
    private class LoadTask extends AsyncTask<String, Integer, List<JtopicModel>> {
        private int currentItemCount;

        public LoadTask(int currentItemCount) {
            this.currentItemCount = currentItemCount;
        }


        @Override
        protected List<JtopicModel> doInBackground(String... params) {
            JtopicModel topicModel = new JtopicModel();
            return topicModel.loadFromDb(params[0], currentItemCount);
        }

        @Override
        protected void onPostExecute(List<JtopicModel> jtopicModels) {
            super.onPostExecute(jtopicModels);
            jsoupFragment.onLoad(jtopicModels);
        }
    }


}
