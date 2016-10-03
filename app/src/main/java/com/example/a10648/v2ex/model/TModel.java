package com.example.a10648.v2ex.model;

/**
 * Created by 10648 on 2016/10/3 0003.
 */

public class TModel {

    public String title;

    public String url;

    public String content;

    AMember mMember;

    public long created;

    public int replies;

    public TModel(String title, String url, String content, AMember member, long created, int replies) {
        this.title = title;
        this.url = url;
        this.content = content;
        mMember = member;
        this.created = created;
        this.replies = replies;
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

    public AMember getMember() {
        return mMember;
    }

    public void setMember(AMember member) {
        mMember = member;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }


}





