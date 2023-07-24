package com.example.api_project.model;

public enum ResultsCode {

    SUCCESS(200, "成功"),

    FAIL(-1, "失败"),

    ERROR(500, "服务器异常"),

    UNAUTHORIZED(401, "未认证（签名错误）"),

    FORBIDDEN(403, "禁止访问"),

    NOT_FOUND(404, "接口不存在"),

    AUTH_ERROR(-10000, "鉴权登陆失败，请重新登录！");

    public int code;
    public String message;

    ResultsCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}