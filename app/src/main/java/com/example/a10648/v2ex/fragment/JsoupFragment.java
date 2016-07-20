package com.example.a10648.v2ex.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.JRecycleViewAdapter;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.jsoup.MyJsoup;
import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.model.TopicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬数据的通用fragment
 */
public class JsoupFragment extends Fragment {
    //参数传递的键值
    private static final String ARG = "arg";

    RecyclerView j_recycle_view;

    String tab_url;

    List<JtopicModel> jtopicModels = new ArrayList<>();

    MyJsoup myJsoup;


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

        j_recycle_view = (RecyclerView)j_view.findViewById(R.id.j_recycle_view);

        initRecyclerView();
        return j_view;
    }


    public void initRecyclerView () {
        j_recycle_view.setItemAnimator(new DefaultItemAnimator());
        j_recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));

        new JsoupTask().execute();

        JRecycleViewAdapter jRecycleViewAdapter = new JRecycleViewAdapter(jtopicModels, getActivity());
        j_recycle_view.setAdapter(jRecycleViewAdapter);


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

    }
}
