package com.ai.web;

import com.ai.common.CommonUtil;
import com.ai.common.permission.DefaultTokenManager;
import com.ai.domain.login.LoginData;
import com.ai.common.ResponseBuilder;
import com.ai.domain.user.User;
import com.ai.service.LoginService;
import com.ai.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.ai.common.CommonConst.*;
import static com.ai.common.CommonUtil.setUserContext;
import static com.ai.common.ResponseBuilder.REPEAT;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    DefaultTokenManager manager;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/goRegister", method = RequestMethod.POST)
    public Object goRegister(HttpServletRequest request, @RequestBody User user, HttpServletResponse response) {
        User resUser = userService.getUserByName(user.getUserName());
        if (resUser != null) {
            return ResponseBuilder.failure("用户名已存在");
        }
        String myLoveLock = CommonUtil.produceLoveLock(user);
        User loveUser = null;
        if (!CommonUtil.stringIsEmpty(user.getLoveLock())) {
            loveUser = userService.getLoveLock(user);
            if (loveUser == null) {
                return ResponseBuilder.failure("Love-Lock无效");
            } else {
                if (!loveUser.isSingle()) {
                    return ResponseBuilder.failure("请不要挖墙脚");
                } else {
                    if (loveUser.getSex() + user.getSex() != 3) {
                        return ResponseBuilder.failure("请为了人类繁衍着想");
                    } else {
                        loveUser.setLoveLock(myLoveLock);
                        loveUser.setSingle(false);
                        user.setSingle(false);
                    }
                }
            }
        }
        user.setMyLoveLock(myLoveLock);
        user.setPassword(CommonUtil.produceLockPassword(user));
        userService.saveUser(user, loveUser);
        return ResponseBuilder.success("注册成功");
    }

    @RequestMapping(value = "/goLogin", method = RequestMethod.POST)
    public Object goLogin(HttpServletRequest request, @RequestBody User user, HttpServletResponse response) {
        if (manager.checkRepeatLogin(request, CommonUtil.getToken(request))) {
            return ResponseBuilder.success(REPEAT, "用户已登录", null);
        }
        User resUserByName = userService.getUserByName(user.getUserName());
        if (resUserByName == null) {
            return ResponseBuilder.failure("用户不存在");
        }
        User resUser = userService.getUser(user);
        if (resUser == null) {
            return ResponseBuilder.failure("用户密码不正确");
        } else {
            LoginData data = new LoginData();
            data.setLoginUser(user.getUserName());
            data.setLoginIp(user.getLastLoginIp());
            data.setLoginAddress(user.getLastLoginAddress());
            data.setLoginTime(user.getLastLoginTime());
            loginService.saveLoginData(data);

            userService.updateUser(user);
            setUserContext(request, resUser);

            Cookie cookie = new Cookie(TOKEN_NAME, manager.createToken(resUser).getToken());
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseBuilder.success("登录成功", resUser);
        }
    }
}
