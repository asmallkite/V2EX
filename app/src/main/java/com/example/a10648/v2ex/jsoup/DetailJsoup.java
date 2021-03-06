package com.example.a10648.v2ex.jsoup;



import com.example.a10648.v2ex.model.CommentModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    Elements content_1;//二级界面的文章内容
    Elements comments; //评论内容

    //获取的文章内容
    List<String> imgs = new ArrayList<>();
//    HashMap<String,String> imgs = new HashMap<String,String>();
    String content_details = null;
    String img_url = null;

    List<CommentModel> commentModelList = new ArrayList<>();


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

    public String get_final_content () {
        content_1 = doc.select("div[id~=^Wrapper$]").select("div[class~=^topic_content$]");
        content_details = content_1.toString();
        return content_details;
    }

//    public List<String> getContentImgs () {
//
//        content_1 = doc.select("div[id~=^Wrapper$]").select("div[class~=^topic_content$]");
//        Elements media = content_1.select("[src]");
//        for (Element src : media) {
//            if (src.tagName().equals("img"))
//            {
//                img_url = src.attr("abs:src");
//                imgs.add(img_url);
//            }
//        }
//        return imgs;
//    }

    public List<CommentModel> getCommentModel () {
        comments = doc.select("div[id~=^Wrapper$]").select("div[id~=^r_.+]");

        for (Element comment : comments) {
            String avatar_temp = comment.select("img[src~=^//cdn.v2ex.co/.+]").attr("src");
            String avatar = "https:" + avatar_temp;

            String name = comment.select("strong").select("a[class~=^dark$]").first().text();
            String create = comment.select("span[class~=^fade small$]").first().text();
            String comment_content = comment.select("div[class~=^reply_content$]").text();
            String replies = comment.select("span[class~=^no$]").text();

            CommentModel a_comment = new CommentModel(avatar, name, create, comment_content, replies);
            commentModelList.add(a_comment);
        }
        return commentModelList;

    }
}
