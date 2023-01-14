import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


//********************************供应商**************************************************** */
//查询供应商仓库（不分页)
export function supplierInventoryList(data) {
    return request({
        url: baseUrl + "/SupplierInventory/list",
        method: "get",
        params: data,
    });
}

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
//查询零售商仓库（getByshopCode）
export function getByshopCode(data) {
    return request({
        url: baseUrl + "/ShopInventory/getByshopCode",
        method: "get",
        params: data,
    });
}

//查询零售商仓库（分页)
export function ShopInventoryListPage(data) {
    return request({
        url: baseUrl + "/ShopInventory/list-page",
        method: "get",
        params: data,
    });
}

//查询零售商仓库（不分页)
export function ShopInventoryList(data) {
    return request({
        url: baseUrl + "/ShopInventory/list",
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

//查询零售商商品库存（分页)
export function shopkeeperWarehouseListPage(data) {
    return request({
        url: baseUrl + "/shopkeeperWarehouse/list-page",
        method: "get",
        params: data,
    });
}

//查询零售商商品库存（不分页)
export function shopkeeperWarehouseList(data) {
    return request({
        url: baseUrl + "/shopkeeperWarehouse/list",
        method: "get",
        params: data,
    });
}

//添加零售商商品库存
export function shopkeeperWarehouseAdd(data) {
    return request({
        url: baseUrl + "/shopkeeperWarehouse/add",
        method: "POST",
        data: data,
    });
}

//更新零售商商品库存
export function shopkeeperWarehouseUpdate(data) {
    return request({
        url: baseUrl + "/shopkeeperWarehouse/update",
        method: "POST",
        data: data,
    });
}

//删除零售商商品库存
export function shopkeeperWarehouseDelete(data) {
    return request({
        url: baseUrl + "/shopkeeperWarehouse/delete",
        method: "POST",
        data: data,
    });
}

//删除零售商商品库存(列表)
export function shopkeeperWarehouseDeleteList(data) {
    return request({
        url: baseUrl + "/shopkeeperWarehouse/delete-list",
        method: "POST",
        data: data,
    });
}

//查询出入库明细表（分页)
export function detailWarehouseListPage(data) {
    return request({
        url: baseUrl + "/detailWarehouse/list-page",
        method: "get",
        params: data,
    });
}

//添加出入库明细表
export function detailWarehouseAdd(data) {
    return request({
        url: baseUrl + "/detailWarehouse/add",
        method: "POST",
        data: data,
    });
}

//更新出入库明细表
export function detailWarehouseUpdate(data) {
    return request({
        url: baseUrl + "/detailWarehouse/update",
        method: "POST",
        data: data,
    });
}

//删除出入库明细表
export function detailWarehouseDelete(data) {
    return request({
        url: baseUrl + "/detailWarehouse/delete",
        method: "POST",
        data: data,
    });
}

//删除出入库明细表(列表)
export function detailWarehouseDeleteList(data) {
    return request({
        url: baseUrl + "/detailWarehouse/delete-list",
        method: "POST",
        data: data,
    });
}