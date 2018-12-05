package com.ai.service.connection;


import com.ai.domain.user.User;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

public class UserStatus {
    private User user;
    private Date lastHeartTime;
    private int failNum;
    private WebSocketSession socketSession;

    public static final int MAX_HEART = 7;

    public static final int MAX_FAIL_NUM = 5;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getLastHeartTime() {
        return lastHeartTime;
    }

    public void setLastHeartTime(Date lastHeartTime) {
        this.lastHeartTime = lastHeartTime;
    }

    public int getFailNum() {
        return failNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }

    public WebSocketSession getSocketSession() {
        return socketSession;
    }

    public void setSocketSession(WebSocketSession socketSession) {
        this.socketSession = socketSession;
    }
}
