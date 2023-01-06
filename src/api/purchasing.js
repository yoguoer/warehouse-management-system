import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


//********************************供应商**************************************************** */
//查询采购订单（分页)
export function inputWarehouseListPage(data) {
    return request({
      url: baseUrl + "/inputWarehouse/list-page",
      method: "get",
      params: data,
    });
  }
  
//添加采购订单
export function inputWarehouseAdd(data) {
return request({
    url: baseUrl + "/inputWarehouse/add",
    method: "POST",
    data: data,
});
}

//更新采购订单
export function inputWarehouseUpdate(data) {
return request({
    url: baseUrl + "/inputWarehouse/update",
    method: "POST",
    data: data,
});
}

//删除采购订单
export function inputWarehouseDelete(data) {
return request({
    url: baseUrl + "/inputWarehouse/delete",
    method: "POST",
    data: data,
});
}

//删除采购订单(列表)
export function inputWarehouseDeleteList(data) {
return request({
    url: baseUrl + "/inputWarehouse/delete-list",
    method: "POST",
    data: data,
});
}