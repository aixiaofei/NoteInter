package com.ai.common.permission;

import com.ai.domain.user.User;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by aixiaofei on 2018/11/8.
 */
public interface TokenManager {
    Token createToken(User user);
    boolean checkToken(String token);
    boolean checkTokenInvlid(HttpServletRequest request);
    void deleteToken(String token);
    Token getToken(String token);
}
