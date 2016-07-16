package com.example.a10648.v2ex.model;

/**
 * Created by 10648 on 2016/7/15 0015.
 */
public class TopicModel {

    public String title;

    public String url;

    public String content;

    public String avatar;

    public String username;

    public long created;


    public TopicModel(String title, String url, String content, String avatar, String username, long created) {
        this.title = title;
        this.url = url;
        this.content = content;
        this.avatar = avatar;
        this.username = username;
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
