package com.example.a10648.v2ex.jsoup;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 利用Jsoup和正则表达式的语法 去做网络蜘蛛爬取网络数据
 * 二级页面获取
 *  李争
 *  2016/7/20
 */
public class DetailJsoup {
    public static final String TAG = "DetailJsoup";

    String url_detail;

    Document doc;
    Elements string;//二级界面的文章内容

    String content_details = null; //获取的文章内容


    public DetailJsoup(String url_detail) {
        this.url_detail = url_detail;
    }


    /**
     * 利用Jsoup连接网路
     */
    public void Jconn () {
        try {
            
            doc = Jsoup.connect(url_detail).get();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContentDetails () {

        string = doc.select("div[id~=^Wrapper$]").select("div[class~=^topic_content$]");
        content_details = string.toString();

        Log.d(TAG, content_details);

        return content_details;
    }
}
