import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


//********************************供应商**************************************************** */
//查询供应商仓库（分页)
export function supplierInventoryListPage(data) {
    return request({
      url: baseUrl + "/SupplierInventory/list-page",
      method: "get",
      params: data,
    });
  }
  
//添加供应商仓库
export function supplierInventoryAdd(data) {
return request({
    url: baseUrl + "/SupplierInventory/add",
    method: "POST",
    data: data,
});
}

//更新供应商仓库
export function SupplierInventoryUpdate(data) {
return request({
    url: baseUrl + "/SupplierInventory/update",
    method: "POST",
    data: data,
});
}

//删除供应商仓库
export function supplierInventoryDelete(data) {
return request({
    url: baseUrl + "/SupplierInventory/delete",
    method: "POST",
    data: data,
});
}

//删除供应商仓库(列表)
export function supplierInventoryDeleteList(data) {
return request({
    url: baseUrl + "/SupplierInventory/delete-list",
    method: "POST",
    data: data,
});
}

//********************************零售商**************************************************** */
//查询零售商仓库（分页)
export function shopkeeperInventoryListPage(data) {
    return request({
      url: baseUrl + "/shopkeeperInventory/list-page",
      method: "get",
      params: data,
    });
  }
  
//添加零售商仓库
export function shopkeeperInventoryAdd(data) {
return request({
    url: baseUrl + "/shopkeeperInventory/add",
    method: "POST",
    data: data,
});
}

//更新零售商仓库
export function shopkeeperInventoryUpdate(data) {
return request({
    url: baseUrl + "/shopkeeperInventory/update",
    method: "POST",
    data: data,
});
}

//删除零售商仓库
export function shopkeeperInventoryDelete(data) {
return request({
    url: baseUrl + "/shopkeeperInventory/delete",
    method: "POST",
    data: data,
});
}

//删除零售商仓库(列表)
export function shopkeeperInventoryDeleteList(data) {
return request({
    url: baseUrl + "/shopkeeperInventory/delete-list",
    method: "POST",
    data: data,
});
}