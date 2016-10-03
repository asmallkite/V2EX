package com.example.a10648.v2ex;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;



import java.io.File;

/**
 * Created by 10648 on 2016/7/15 0015.
 */
public class MyApplication extends Application {
    private static Application mContext;
    private static int sMemoryClass;
    public static  int isNetWorkConnected; //全局网络状态 没有网络0：WIFI网络1：3G网络2：2G网络3

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;


        initAppConfig();
    }

    private void initAppConfig() {
        final ActivityManager mgr = (ActivityManager) getApplicationContext().
                getSystemService(Activity.ACTIVITY_SERVICE);
        sMemoryClass = mgr.getMemoryClass();
    }

    public static Application getInstance(){
        return mContext;
    }

    public int getMemorySize(){
        return sMemoryClass;
    }

    public static Context getContext(){
        return mContext;
    }
}
