package com.example.a10648.v2ex.presenter.IView;

import com.example.a10648.v2ex.model.TModel;

import java.util.ArrayList;

/**
 * Created by 10648 on 2016/10/3 0003.
 */

public interface ITFragment  extends IBaseFragment{

    void updateTopics(ArrayList<TModel> models);
}
