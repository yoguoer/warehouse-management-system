package com.example.api_project.model;


import java.util.List;

import static com.example.api_project.model.ResultsCode.AUTH_ERROR;

public class ResponseData {
    /****
     * 成功响应Msg中的信息
     **/
    private final static String SUCCESS = "success";

    /****
     * 失败响应Msg中的信息
     **/
    private final static String FAILED = "failed";

    private final static String FORBIDDEN = "forbidden";


    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(ResultsCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    public static Result success() {
        return new Result().setCode(ResultsCode.SUCCESS).setMsg(SUCCESS);
    }

    public static Result forbidden() {
        return new Result().setCode(ResultsCode.FORBIDDEN).setMsg(FORBIDDEN);
    }

    public static Result authError() {
        return error(AUTH_ERROR);
    }

    public static <T> Result<T> error(ResultsCode resultsCode) {
        return new Result<T>().setCode(resultsCode.code).setMsg(resultsCode.message);
    }


    public static <T> Result<T> error(String mesage) {
        return new Result<T>().setMsg(mesage).setCode(ResultsCode.FAIL);
    }

    public static <T> Result<T> error(String mesage, ResultsCode resultsCode) {
        return new Result<T>().setMsg(mesage).setCode(resultsCode.code);
    }

    public static <T> Result<T> error() {
        return new Result<T>().setCode(ResultsCode.FAIL).setMsg(FAILED);
    }

    public static <T> Result<T> Forbidden() {
        return new Result<T>().setCode(ResultsCode.FORBIDDEN).setMsg(FORBIDDEN);
    }
}
