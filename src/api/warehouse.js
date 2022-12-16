import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


// 用户登录
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