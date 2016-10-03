package com.example.a10648.v2ex.presenter.ImplModel;

import com.example.a10648.v2ex.api.ApiManage;
import com.example.a10648.v2ex.model.TModel;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.presenter.IModel.ITopic;
import com.example.a10648.v2ex.presenter.IView.ITFragment;

import java.util.ArrayList;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 10648 on 2016/10/3 0003.
 */

public class ImplTopic extends ImplBaseModel implements ITopic {
    private ITFragment mITFragment;

    public ImplTopic(ITFragment ITFragment) {
        mITFragment = ITFragment;
    }

    @Override
    public void getTopicData(String category) {
        Subscription subscription = ApiManage.getInstance().getTopicService()
                .getTopicData(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<TModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mITFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<TModel> models) {
                        mITFragment.updateTopics(models);
                    }
                });
    }
 }

