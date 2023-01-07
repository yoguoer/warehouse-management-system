import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


//********************************供应商**************************************************** */
//查询采购订单（分页)
export function outputWarehouseListPage(data) {
    return request({
      url: baseUrl + "/outputWarehouse/list-page",
      method: "get",
      params: data,
    });
  }
  
//添加采购订单
export function outputWarehouseAdd(data) {
return request({
    url: baseUrl + "/outputWarehouse/add",
    method: "POST",
    data: data,
});
}

//更新采购订单
export function outputWarehouseUpdate(data) {
return request({
    url: baseUrl + "/outputWarehouse/update",
    method: "POST",
    data: data,
});
}

//删除采购订单
export function outputWarehouseDelete(data) {
return request({
    url: baseUrl + "/outputWarehouse/delete",
    method: "POST",
    data: data,
});
}

//删除采购订单(列表)
export function outputWarehouseDeleteList(data) {
return request({
    url: baseUrl + "/outputWarehouse/delete-list",
    method: "POST",
    data: data,
});
}