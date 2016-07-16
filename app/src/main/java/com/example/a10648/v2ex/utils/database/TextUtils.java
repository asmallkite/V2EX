package com.example.a10648.v2ex.utils.database;

import android.content.Context;
import android.text.format.DateUtils;

import com.example.a10648.v2ex.R;

/**
 * Created by 10648 on 2016/7/15 0015.
 */
public class TextUtils {

    public static CharSequence getRelativeTimeDisplayString(Context context, long referenceTime) {
        long now = System.currentTimeMillis();
        long difference = now - referenceTime;
        return (difference >= 0 &&  difference<= DateUtils.MINUTE_IN_MILLIS) ?
                context.getResources().getString(R.string.just_now):
                DateUtils.getRelativeTimeSpanString(
                        referenceTime,
                        now,
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE);
    }

}
