package com.example.a10648.v2ex.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.a10648.v2ex.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 10648 on 2016/7/11 0011.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.a);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 这个方法主要用来将Calligraphy  Inject into Context
     * @param newBase
     */
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
//    }
}
