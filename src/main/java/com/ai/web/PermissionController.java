package com.ai.web;

import com.ai.common.ResponseBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aixiaofei on 2018/11/8.
 */
@RestController
public class PermissionController {
    @RequestMapping(value = "/checkPermission")
    public Object check(){
        return ResponseBuilder.success(ResponseBuilder.REPEAT, "用户已登录", null);
    }
}
