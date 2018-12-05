package com.ai.domain.login;

import java.util.Date;

public class LoginData {
    private String LoginUser;
    private String LoginIp;
    private String LoginAddress;
    private Date LoginTime;

    public String getLoginUser() {
        return LoginUser;
    }

    public void setLoginUser(String loginUser) {
        LoginUser = loginUser;
    }

    public String getLoginIp() {
        return LoginIp;
    }

    public void setLoginIp(String loginIp) {
        LoginIp = loginIp;
    }

    public String getLoginAddress() {
        return LoginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        LoginAddress = loginAddress;
    }

    public Date getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(Date loginTime) {
        LoginTime = loginTime;
    }
}
