import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


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