package com.ai.common.permission;

import com.ai.common.CommonConst;
import com.ai.common.CommonUtil;
import com.ai.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aixiaofei on 2018/11/8.
 */
@Component
public class DefaultTokenManager implements TokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTokenManager.class);

    private static Map<String, Token> TokenMap = new HashMap<String, Token>();

    public Token createToken(User user) {
        String info = user.getUserName() + "," + user.getLastLoginIp() + "," + user.getLastLoginTime();
        String token = null;
        token = CommonUtil.Base64Encode(info);
        Token resToken = new Token(token, user);
        TokenMap.put(token, resToken);
        return resToken;
    }

    public boolean checkRepeatLogin(HttpServletRequest request, String token){
        HttpSession session = request.getSession();
        return !CommonUtil.stringIsEmpty(token) && TokenMap.containsKey(token) && session.getAttribute(CommonConst.USER_CONTEXT) != null;
    }

    public boolean checkToken(String token) {
        return (CommonUtil.stringIsEmpty(token) || !TokenMap.containsKey(token));
    }

    public boolean checkTokenInvlid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute(CommonConst.USER_CONTEXT) == null;
    }

    public void deleteToken(String token) {
        TokenMap.remove(token);
    }

    public Token getToken(String token) {
        return TokenMap.get(token);
    }
}
