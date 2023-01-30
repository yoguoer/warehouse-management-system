import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"

//查询盘亏盘盈审批（通过key查单个）
export function countCheckGetById(data) {
    return request({
        url: baseUrl + "/countCheck/getById",
        method: "get",
        params: data,
    });
}

//查询盘亏盘盈审批（分页）
export function countCheckListPage(data) {
    return request({
        url: baseUrl + "/countCheck/list-page",
        method: "get",
        params: data,
    });
}

//查询盘亏盘盈审批（不分页）
export function countCheckList(data) {
    return request({
        url: baseUrl + "/countCheck/list",
        method: "get",
        params: data,
    });
}


//添加盘亏盘盈审批
export function countCheckAdd(data) {
    return request({
        url: baseUrl + "/countCheck/add",
        method: "POST",
        data: data,
    });
}

//更新盘亏盘盈审批
export function countCheckUpdate(data) {
    return request({
        url: baseUrl + "/countCheck/update",
        method: "POST",
        data: data,
    });
}

//查询采购统计（分页）
export function purchaseIntegrateListPage(data) {
    return request({
        url: baseUrl + "/purchaseIntegrate/list-page",
        method: "get",
        params: data,
    });
}

//查询销售统计（分页）
export function salesIntegrateListPage(data) {
    return request({
        url: baseUrl + "/salesIntegrate/list-page",
        method: "get",
        params: data,
    });
}

//查询调货统计（分页）
export function transferIntegrateListPage(data) {
    return request({
        url: baseUrl + "/transferIntegrate/list-page",
        method: "get",
        params: data,
    });
}