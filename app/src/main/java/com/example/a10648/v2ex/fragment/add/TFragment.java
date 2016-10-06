package com.example.a10648.v2ex.fragment.add;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a10648.v2ex.R;
import com.example.a10648.v2ex.adapter.MyRecyclerViewAdapter2;
import com.example.a10648.v2ex.adapter.TAdapter;
import com.example.a10648.v2ex.model.TModel;
import com.example.a10648.v2ex.presenter.IView.ITFragment;
import com.example.a10648.v2ex.presenter.ImplModel.ImplTopic;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 10648 on 2016/10/3 0003.
 *
 */

public class TFragment extends BaseFragment implements ITFragment{

    public static final String TAG = "TFragment";

    @InjectView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    ImplTopic mImplTopic;
    private List<TModel> linkList = new ArrayList<>();

    TAdapter adapter2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_and__latest_, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mImplTopic = new ImplTopic(this);
    }

    private void initView() {
        adapter2 = new TAdapter(linkList, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter2);
        loadData();
    }

    private void loadData() {
        if (adapter2.getItemCount() > 0) {
            adapter2.clearData();
        }
        mImplTopic.getTopicData("hot.json");
    }

    @Override
    public void updateTopics(ArrayList<TModel> models) {
        adapter2.addItems(models);

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), "无网络", Toast.LENGTH_SHORT).show();
        Log.d(TAG, error);
    }
}
