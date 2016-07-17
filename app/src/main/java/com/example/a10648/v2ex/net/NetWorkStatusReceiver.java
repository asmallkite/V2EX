package com.example.a10648.v2ex.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.a10648.v2ex.MyApplication;

/**
 * Created by 10648 on 2016/7/17 0017.
 * 网络Broadcast
 */
public class NetWorkStatusReceiver extends BroadcastReceiver {

    public NetWorkStatusReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                /**
                 * 每当网络发生变化时，onReceive 就执行，提示网络状态变化
                 */
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "网 络 已 连 接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "网 络 不 可 用", Toast.LENGTH_SHORT).show();
            }

            MyApplication.isNetWorkConnected = NetWorkUtils.getAPNType(context)>0;
        }
    }
}