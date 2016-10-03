package com.example.a10648.v2ex.model;

public class AMember {
   public String avatar;

   public String username;

    public AMember(String avatar, String username) {
        this.avatar = avatar;
        this.username = username;
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
}
