package com.example.a10648.v2ex.jsoup;

import android.util.Log;

import com.example.a10648.v2ex.model.JtopicModel;
import com.example.a10648.v2ex.model.TopicModel;
import com.example.a10648.v2ex.utils.database.Str2No;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用Jsoup和正则表达式的语法 去做网络蜘蛛爬取网络数据
 * @author： 李争
 * @date: 2016/7/19
 */
public class MyJsoup {
    /**
     * 要爬取的网络链接 由两部分组成。base_url tab_url
     */
    String base_url = "http://www.v2ex.com/";
    String tab_url ;


    public static final String TECH = "?tab=tech";

    public static final String CREATIVE = "?tab=creative";

    public static final String PLAY = "?tab=play";

    public static final String APPLE = "?tab=apple";

    public static final String JOBS = "?tab=jobs";

    public static final String DEALS = "?tab=deals";

    public static final String CITY = "?tab=city";

    public static final String QNA = "?tab=qna";

    public static final String HOT = "?tab=hot";

    public static final String ALL = "?tab=all";

    public static final String R2 = "?tab=r2";

    Document doc;

    /**
     * 要存放的List<TopicModel>
     */
    List<JtopicModel> JtopicModeList = new ArrayList<>();

    Elements elements;




    /**
     * 构造方法，传入tab_url,构造相应的url
     */
    public MyJsoup(String tab_url) {
        this.tab_url = tab_url;
    }

    /**
     * 利用Jsoup连接网路
     */
    public void Jconn () {
        try {
            doc = Jsoup.connect(base_url + tab_url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取，爬取数据。构造TopicModel
     */
    public List<JtopicModel> getTopicModel () {
        elements = doc.select("div[id~=^Wrapper$]").select("div[class~=^cell item$]");
        for (Element element : elements) {
            String title = element.select("span[class~=^item_title$]").select("a").text();

            String url = element.select("span[class~=^item_title$]").select("a").attr("abs:href");

            String avatar_temp = element.select("img[src~=^//cdn.v2ex.co/.+]").attr("src");
            String avatar = "https:" + avatar_temp;

            String username = element.select("span[class~=^small fade$]").select("strong").first().select("a").text();

            String created = element.select("span[class~=^small fade$]").select(":contains(前)").text();

            String replies = element.select("td[align~=^right$]").select("a").text();

            String nodename = element.select("span[class~=^small fade$]").select("a").first().text();


            JtopicModel jtopicModel = new JtopicModel(avatar, url, title, nodename, created, username, replies);

            JtopicModeList.add(jtopicModel);
        }
        return  JtopicModeList;

    }

}
