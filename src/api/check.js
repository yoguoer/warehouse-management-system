import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"

//查询采购退货单（分页)
export function returnCheckListPage(data) {
  return request({
    url: baseUrl + "/returnCheck/list-page",
    method: "get",
    params: data,
  });
}

//添加采购退货单
export function returnCheckAdd(data) {
  return request({
    url: baseUrl + "/returnCheck/add",
    method: "POST",
    data: data,
  });
}

//更新采购退货单
export function returnCheckUpdate(data) {
  return request({
    url: baseUrl + "/returnCheck/update",
    method: "POST",
    data: data,
  });
}

//删除采购退货单
export function returnCheckDelete(data) {
  return request({
    url: baseUrl + "/returnCheck/delete",
    method: "POST",
    data: data,
  });
}

//删除采购退货单(列表)
export function returnCheckDeleteList(data) {
  return request({
    url: baseUrl + "/returnCheck/delete-list",
    method: "POST",
    data: data,
  });
}

//查询采购退货单inputOutputKey
export function returnCheckByKey(data) {
  return request({
    url: baseUrl + "/returnCheck/getById",
    method: "get",
    params: data,
  });
}

//查询采购调货单（分页)
export function transferCheckListPage(data) {
  return request({
    url: baseUrl + "/transferCheck/list-page",
    method: "get",
    params: data,
  });
}

//添加采购调货单
export function transferCheckAdd(data) {
  return request({
    url: baseUrl + "/transferCheck/add",
    method: "POST",
    data: data,
  });
}

//更新采购调货单
export function transferCheckUpdate(data) {
  return request({
    url: baseUrl + "/transferCheck/update",
    method: "POST",
    data: data,
  });
}

//删除采购调货单(列表)
export function transferCheckDelete(data) {
  return request({
    url: baseUrl + "/transferCheck/delete",
    method: "POST",
    data: data,
  });
}

//删除采购调货单(列表)
export function transferCheckDeleteList(data) {
  return request({
    url: baseUrl + "/transferCheck/delete-list",
    method: "POST",
    data: data,
  });
}