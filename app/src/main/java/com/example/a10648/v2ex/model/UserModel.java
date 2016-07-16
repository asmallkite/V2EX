package com.example.a10648.v2ex.model;

/**
 * Created by 10648 on 2016/7/16 0016.
 */
public class UserModel {
    public String user_name;

    public String tagline;

    public String bio;

    public String avatar_large;

    public UserModel() {
    }

    public UserModel(String user_name, String tagline, String bio, String avatar_large) {
        this.user_name = user_name;
        this.tagline = tagline;
        this.bio = bio;
        this.avatar_large = avatar_large;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }
}
