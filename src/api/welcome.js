import http from '@/utils/http'
import qs from "qs";
import request from "../utils/request";

const baseUrl = "/api"


//查询商品
export function alertInfolistPage(data) {
  return request({
    url: baseUrl + "/alertInfo/list-page",
    method: "get",
    params: data,
  });
}

//新增商品
export function alertInfoAdd(data) {
  return request({
    url: baseUrl + "/alertInfo/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新商品
export function alertInfoUpdate(data) {
  return request({
    url: baseUrl + "/alertInfo/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除商品
export function alertInfoDelete(data) {
  return request({
    url: baseUrl + "/alertInfo/delete",
    method: "POST",
    data: data,
  });
}

//删除商品(列表)
export function alertInfoDeleteList(data) {
  return request({
    url: baseUrl + "/alertInfo/delete-list",
    method: "POST",
    data: data,
  });
}