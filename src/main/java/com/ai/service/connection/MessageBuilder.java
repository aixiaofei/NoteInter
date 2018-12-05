package com.ai.service.connection;

import com.alibaba.fastjson.JSON;

public class MessageBuilder {

    public static String produceHeart(){
        return JSON.toJSONString(new Message(Message.HEART_PACKAGE, "心跳"));
    }

    public static String produceMessage(int code, String data){
        return JSON.toJSONString(new Message(code, data));
    }

    public static Message getMessageFromString(String message) {
        return JSON.parseObject(message, Message.class);
    }
}
