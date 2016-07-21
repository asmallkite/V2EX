package com.example.a10648.v2ex.model;

/**
 * Created by 10648 on 2016/7/20 0020.
 * 评论的model
 */
public class CommentModel {
    public String avatar;

    public String name;

    public String create;

    public String comment;

    public String replies;


    public CommentModel(String avatar, String name, String create, String comment, String replies) {
        this.avatar = avatar;
        this.name = name;
        this.create = create;
        this.comment = comment;
        this.replies = replies;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }
}
