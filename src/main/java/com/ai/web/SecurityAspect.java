package com.ai.web;

import com.ai.common.CommonUtil;
import com.ai.common.ResponseBuilder;
import com.ai.common.permission.DefaultTokenManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by aixiaofei on 2018/11/8.
 */
@Aspect
@Component
public class SecurityAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityAspect.class);

    @Autowired
    private DefaultTokenManager manager;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) && !target(com.ai.web.LoginController)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = CommonUtil.getToken(request);
        if (manager.checkToken(token)) {
            return ResponseBuilder.failure(ResponseBuilder.INVALID, "用户未登录");
        }
        if (manager.checkTokenInvlid(request)) {
            manager.deleteToken(token);
            return ResponseBuilder.failure(ResponseBuilder.EXPIRED, "会话已失效");
        }
        return joinPoint.proceed();
    }
}
