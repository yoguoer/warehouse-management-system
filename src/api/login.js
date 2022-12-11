import http from '@/utils/http'
import qs from "qs";
import request from "../utils/request";

//
/**
 *  @parms resquest 请求地址 例如：http://197.82.15.15:8088/request/...
 *  @param '/testIp'代表vue-cil中config，index.js中配置的代理
 */
// let baseURL = "/ssm_project_war"
let baseURL = "/api"


// 用户登录
export function toLogin(data) {
  return request({
    url: baseURL + "/login/login" ,
    method: "post",
    data: qs.parse(data),
  });
}

// 用户注册
export function toRegister(data) {
  return request({
    url: baseURL + "/login/register" ,
    method: "post",
    data: qs.parse(data),
  });
}

// 用户信息
export function getUserInfo(data) {
  return request({
    url: baseURL + "/login/getUserInfo" ,
    method: "post",
    data: qs.parse(data),
  });
}