package com.example.a10648.v2ex.api;

import com.example.a10648.v2ex.model.TModel;
import com.example.a10648.v2ex.model.TopicModel;


import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 10648 on 2016/10/3 0003.
 *
 */

public interface TopicApi {

    @GET("/api/topics/{category}")
    Observable<ArrayList<TModel>> getTopicData(@Path("category") String category);
}
