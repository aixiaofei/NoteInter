package com.ai.web;

import com.ai.common.ResponseBuilder;
import com.ai.service.file.OosFileQianNiu;
import com.ai.service.file.OosFileTencent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping(value = "/common")
public class CommonController {

    @Autowired
    private OosFileQianNiu oosFileQianNiu;

    @Autowired
    private OosFileTencent oosFileTencent;

    @RequestMapping(value = "/getFileSignQianNiu", method = RequestMethod.POST)
    public Object getFileSign(@RequestBody Map param) {
        String key = String.valueOf(param.get("key"));
        return ResponseBuilder.success("获取签名成功", oosFileQianNiu.produceSign(key));
    }

    @RequestMapping(value = "/getFileSignTencent", method = RequestMethod.POST)
    public Object getFileSignTencent(@RequestBody Map param) {
        String key = String.valueOf(param.get("key"));
        String type = String.valueOf(param.get("type"));
        return ResponseBuilder.success("获取签名成功", oosFileTencent.produceSign(key, type));
    }
}
