package com.example.a10648.v2ex.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 10648 on 2016/10/3 0003.
 */

public class ApiManage {
    private static ApiManage apiManage;
    public static ApiManage getInstance() {
        if (apiManage == null) {
            synchronized (ApiManage.class) {
                if (apiManage == null) {
                    apiManage = new ApiManage();
                }
            }
        }
        return apiManage;
    }


    public TopicApi mTopicApi;

    public TopicApi getTopicService() {
        if (mTopicApi == null) {
            synchronized (new Object()) {
                if (mTopicApi == null) {
                    mTopicApi = new Retrofit.Builder()
                            .baseUrl("https://www.v2ex.com")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(TopicApi.class);
                }
            }
        }
        return mTopicApi;
    }

}
