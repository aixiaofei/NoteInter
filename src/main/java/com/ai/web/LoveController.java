package com.ai.web;

import com.ai.common.ResponseBuilder;
import com.ai.dao.Page;
import com.ai.domain.file.File;
import com.ai.domain.user.User;
import com.ai.domain.loveRelation.LoveRelation;
import com.ai.service.LoveService;
import com.ai.service.UserService;
import com.ai.service.file.OosFileQianNiu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/love")
public class LoveController extends BaseController {

    @Autowired
    private LoveService loveService;

    @Autowired
    private UserService userService;

    @Autowired
    private OosFileQianNiu oosFileQianNiu;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoveController.class);

    @RequestMapping(value = "/getLoveInfo")
    public Object getLoveInfo(int userId) {
        User resUser = loveService.getLoveInfo(userId);
        if (resUser != null)
            return ResponseBuilder.success("查询成功", resUser);
        else
            return ResponseBuilder.failure("查询失败");
    }

    @RequestMapping(value = "/getLoveNumber")
    public Object getLoveNumber(int userId) {
        LoveRelation relation = loveService.getLoveNumber(userId);
        if (relation != null)
            return ResponseBuilder.success("查询成功", relation);
        else
            return ResponseBuilder.failure("查询失败");
    }

    @RequestMapping(value = "/checkLoveOnline")
    public Object checkLoveOnline(int userId) {
        boolean online = userService.checkLoveOnline(userId);
        if (online) {
            return ResponseBuilder.success("Love在线");
        } else {
            return ResponseBuilder.failure("Love不在线");
        }
    }

    @RequestMapping(value = "/saveLoveLittle", method = RequestMethod.POST)
    public Object saveLoveLittle(@RequestBody Map map) {
        loveService.saveLoveLittle(map);
        return ResponseBuilder.success("保存成功");
    }

    @RequestMapping(value = "/getLoveLittle", method = RequestMethod.POST)
    public Object getLoveLittle(@RequestBody Map map) {
        Page page = loveService.getLoveLittleList(map);
        if (page == null) {
            return ResponseBuilder.failure("查询失败");
        } else {
            return ResponseBuilder.success("查询成功", page.getData() != null ? page.getData() : new ArrayList<>());
        }
    }

    @RequestMapping(value = "/responseLittle")
    public Object responseLittle(int id, int action) {
        LoveRelation relation = loveService.responseLoveLittle(id, action);
        return ResponseBuilder.success("更新成功", relation);
    }

    @RequestMapping(value = "/deleteLittleFile")
    public Object deleteLittleFile(@RequestBody File file) {
        loveService.deleteLittleFile(file);
        return ResponseBuilder.success("删除文件成功");
    }
}
