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
export function ShopInventoryListPage(data) {
    return request({
      url: baseUrl + "/ShopInventory/list-page",
      method: "get",
      params: data,
    });
  }
  
//添加零售商仓库
export function ShopInventoryAdd(data) {
return request({
    url: baseUrl + "/ShopInventory/add",
    method: "POST",
    data: data,
});
}

//更新零售商仓库
export function ShopInventoryUpdate(data) {
return request({
    url: baseUrl + "/ShopInventory/update",
    method: "POST",
    data: data,
});
}

//删除零售商仓库
export function ShopInventoryDelete(data) {
return request({
    url: baseUrl + "/ShopInventory/delete",
    method: "POST",
    data: data,
});
}

//删除零售商仓库(列表)
export function ShopInventoryDeleteList(data) {
return request({
    url: baseUrl + "/ShopInventory/delete-list",
    method: "POST",
    data: data,
});
}