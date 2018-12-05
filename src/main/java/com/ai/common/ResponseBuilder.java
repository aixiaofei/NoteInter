package com.ai.common;

public class ResponseBuilder {

    private static class Response {

        private String code;
        private String statu;
        private String msg;
        private Object data;

        Response(String code, String statu, String msg, Object data) {
            this.code = code;
            this.statu = statu;
            this.msg = msg;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getStatu() {
            return statu;
        }

        public void setStatu(String statu) {
            this.statu = statu;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }

    public static final String EFFECTIVE = "200";
    public static final String INVALID = "201";
    public static final String EXPIRED = "202";
    public static final String REPEAT = "203";

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static Response success() {
        return new Response(EFFECTIVE, SUCCESS, null, null);
    }

    public static Response success(String msg) {
        return new Response(EFFECTIVE, SUCCESS, msg, null);
    }

    public static Response success(String code, String msg, Object data) {
        return new Response(code, SUCCESS, msg, null);
    }

    public static Response success(String msg, Object data) {
        return new Response(EFFECTIVE, SUCCESS, msg, data);
    }

    public static Response failure(String msg) {
        return new Response(EFFECTIVE, FAIL, msg, null);
    }

    public static Response failure(String code, String msg) {
        return new Response(code, FAIL, msg, null);
    }

    public static Response failure(String msg, Object data) {
        return new Response(EFFECTIVE, FAIL, msg, data);
    }
}
