package com.ai.common.permission;

import com.ai.domain.user.User;

/**
 * Created by aixiaofei on 2018/11/8.
 */
public class Token {

    private String token;
    private User user;

    Token(String token,User user){
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
