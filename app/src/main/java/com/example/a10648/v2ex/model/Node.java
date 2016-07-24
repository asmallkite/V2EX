package com.example.a10648.v2ex.model;

/**
 * Created by 10648 on 2016/7/24 0024.
 */
public class Node {
    private String url;
    private String avatar_normal;
    private String title;
    private String header;
    private String topics;

    public Node() {
    }

    public Node(String url, String avatar_normal, String title, String header, String topics) {
        this.url = url;
        this.avatar_normal = avatar_normal;
        this.title = title;
        this.header = header;
        this.topics = topics;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar_normal() {
        return avatar_normal;
    }

    public void setAvatar_normal(String avatar_normal) {
        this.avatar_normal = avatar_normal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}
