package com.example.a10648.v2ex.model;

/**
 * Created by 10648 on 2016/7/19 0019.
 * 此model在抓取数据时使用
 */
public class JtopicModel {

    public String Javatar;

    public String Jurl;

    public String Jtitle;

    public String Jnodename;

    public String Jcreated;

    public String Jusername;

    public String Jreplies;

    public JtopicModel(String javatar, String jurl, String jtitle, String jnodename, String jcreated,
                       String jusername, String jreplies) {
        Javatar = javatar;
        Jurl = jurl;
        Jtitle = jtitle;
        Jnodename = jnodename;
        Jcreated = jcreated;
        Jusername = jusername;
        Jreplies = jreplies;
    }

    public String getJavatar() {
        return Javatar;
    }

    public void setJavatar(String javatar) {
        Javatar = javatar;
    }

    public String getJurl() {
        return Jurl;
    }

    public void setJurl(String jurl) {
        Jurl = jurl;
    }

    public String getJtitle() {
        return Jtitle;
    }

    public void setJtitle(String jtitle) {
        Jtitle = jtitle;
    }

    public String getJnodename() {
        return Jnodename;
    }

    public void setJnodename(String jnodename) {
        Jnodename = jnodename;
    }

    public String getJcreated() {
        return Jcreated;
    }

    public void setJcreated(String jcreated) {
        Jcreated = jcreated;
    }

    public String getJusername() {
        return Jusername;
    }

    public void setJusername(String jusername) {
        Jusername = jusername;
    }

    public String getJreplies() {
        return Jreplies;
    }

    public void setJreplies(String jreplies) {
        Jreplies = jreplies;
    }
}
