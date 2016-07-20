package com.example.a10648.v2ex.utils.database;

/**
 * Created by 10648 on 2016/7/19 0019.
 * 爬取的数据  字符串（包含数字和汉字）
 * 将 数字提取出来
 */
public class Str2No {
    public static  String str2no (String ori_str) {
        String str = ori_str.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0 ; i < str.length(); i ++) {
                if (str.charAt(i) >= 48  &&  str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }
}
