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


// 用户列表
export function getUserList(params) {
  return request({
    url: baseURL + "/user/selectUserPage" ,
    method: "get",
    params: qs.parse(params),
  });
}

//添加用户
export function createUser(data) {
  return request({
    url: baseURL + "/user/createUser" ,
    method: "post",
    data: qs.parse(data),
  });
}

//根据 userId 删除用户
export function deleteUserById(data) {
  return request({
    url: baseURL + "/user/deleteUserById" ,
    method: "get",
    params: qs.parse(data),
  });
}

//根据 userId 批量删除用户
export function deleteUserByIdList(data) {
  return request({
    url: baseURL + "/user/deleteUserByIdList" ,
    method: "get",
    params: qs.parse(data),
  });
}

//根据userId更新用户信息
export function updateUserById(data) {
  return request({
    url: baseURL + "/user/updateUserById" ,
    method: "post",
    data: qs.parse(data),
  });
}
