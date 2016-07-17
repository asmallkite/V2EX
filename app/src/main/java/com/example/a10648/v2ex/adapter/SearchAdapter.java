package com.example.a10648.v2ex.adapter;

import android.content.Context;

import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.utils.database.ViewHolder;

import java.util.List;

/**
 * Created by 10648 on 2016/7/17 0017.
 */
public class SearchAdapter extends CommonAdapter<TopicModel> {

    public SearchAdapter(Context context, List<TopicModel> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {

    }
}
