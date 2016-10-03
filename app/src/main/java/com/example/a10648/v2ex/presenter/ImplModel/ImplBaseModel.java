package com.example.a10648.v2ex.presenter.ImplModel;

import com.example.a10648.v2ex.presenter.IModel.IBaseModel;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 10648 on 2016/10/3 0003.
 */

public class ImplBaseModel implements IBaseModel {

    private CompositeSubscription mCompositeSubscription;

    void addSubsciption(Subscription subscription) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }


    @Override
    public void unSubscibe() {

        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }

    }
}
