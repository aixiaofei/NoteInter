package com.ai.service.connection;

public class Message {
    private int code;
    private String data;

    public static final int LOGIN = 1;
    public static final int UN_LOGIN = 2;
    public static final int EXCEED_TIME = 3;

    public static final int HEART_PACKAGE = 200;
    public static final int MESSAGE_PACKAGE = 201;
    public static final int COMMUNICATION_PACKAGE = 202;

    Message() {
    }

    Message(int code, String data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHeartPackage() {
        return HEART_PACKAGE;
    }

    public int getMessagePackage() {
        return MESSAGE_PACKAGE;
    }
}
