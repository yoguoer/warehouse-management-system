import request from "@/utils/request.js";
import qs from "qs";

const baseUrl = "/api"


//查询分类（树状)
export function getCategoryTree(data) {
  return request({
    url: baseUrl + "/category/tree",
    method: "get",
    params: data,
  });
}

//新增分类
export function addCategory(data) {
  return request({
    url: baseUrl + "/category/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新分类
export function updateCategory(data) {
  return request({
    url: baseUrl + "/category/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除分类
export function deleteCategory(data) {
  return request({
    url: baseUrl + "/category/delete",
    method: "POST",
    data: qs.parse(data),
  });
}
// ==============================================客户================================================
//查询客户
export function CustomerListpage(data) {
  return request({
    url: baseUrl + "/customer/list-page",
    method: "get",
    params: data,
  });
}

//新增客户
export function Customeradd(data) {
  return request({
    url: baseUrl + "/customer/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新客户
export function Customerupdate(data) {
  return request({
    url: baseUrl + "/customer/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除客户
export function Customerdelete(data) {
  return request({
    url: baseUrl + "/customer/delete",
    method: "POST",
    data: data,
  });
}

//删除客户(列表)
export function CustomerdeleteList(data) {
  return request({
    url: baseUrl + "/customer/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================联系人================================================
//查询联系人
export function contactListpage(data) {
  return request({
    url: baseUrl + "/contact/list-page",
    method: "get",
    params: data,
  });
}

//新增联系人
export function Contactadd(data) {
  return request({
    url: baseUrl + "/contact/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新联系人
export function Contactupdate(data) {
  return request({
    url: baseUrl + "/contact/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除联系人
export function Contactdelete(data) {
  return request({
    url: baseUrl + "/contact/delete",
    method: "POST",
    data: data,
  });
}

//删除联系人(列表)
export function ContactdeleteList(data) {
  return request({
    url: baseUrl + "/contact/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================供应商================================================
//查询供应商（分页）
export function SupplierlistPage(data) {
  return request({
    url: baseUrl + "/supplier/list-page",
    method: "get",
    params: data,
  });
}

//根据供应商编号查询供应商
export function SupplierdataById(data) {
  return request({
    url: baseUrl + "/supplier/getById",
    method: "get",
    params: data,
  });
}

//新增供应商
export function Supplieradd(data) {
  return request({
    url: baseUrl + "/supplier/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//编辑供应商
export function Supplierupdate(data) {
  return request({
    url: baseUrl + "/supplier/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除供应商
export function SupplierDelete(data) {
  return request({
    url: baseUrl + "/supplier/delete",
    method: "POST",
    data: data,
  });
}

//删除供应商(列表)
export function SupplierDeleteList(data) {
  return request({
    url: baseUrl + "/supplier/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================品牌================================================
//查询品牌列表
export function brandlistPage(data) {
  return request({
    url: baseUrl + "/brand/list-page",
    method: "get",
    params: qs.parse(data),
  });
}

//更新品牌
export function brandUpdate(data) {
  return request({
    url: baseUrl + "/brand/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//新增品牌
export function brandAdd(data) {
  return request({
    url: baseUrl + "/brand/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除品牌
export function brandDelete(data) {
  return request({
    url: baseUrl + "/brand/delete",
    method: "POST",
    data: data,
  });
}

//删除品牌(列表)
export function brandDeleteList(data) {
  return request({
    url: baseUrl + "/brand/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================银行================================================
//查询银行信息
export function banklistPage(data) {
  return request({
    url: baseUrl + "/supplierBilling/list-page",
    method: "get",
    params: data,
  });
}

//新增银行信息
export function bankAdd(data) {
  return request({
    url: baseUrl + "/supplierBilling/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新银行信息
export function bankUpdate(data) {
  return request({
    url: baseUrl + "/supplierBilling/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除银行信息
export function bankDelete(data) {
  return request({
    url: baseUrl + "/supplierBilling/delete",
    method: "POST",
    data: data,
  });
}

//删除银行信息(列表)
export function bankDeleteList(data) {
  return request({
    url: baseUrl + "/supplierBilling/delete-list",
    method: "POST",
    data: data,
  });
}
// ==============================================车辆================================================
//查询车辆信息
export function vehiclelistPage(data) {
  return request({
    url: baseUrl + "/vehicle/list-page",
    method: "get",
    params: data,
  });
}

//新增车辆信息
export function vehicleAdd(data) {
  return request({
    url: baseUrl + "/vehicle/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新车辆信息
export function vehicleUpdate(data) {
  return request({
    url: baseUrl + "/vehicle/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除车辆信息
export function vehicleDelete(data) {
  return request({
    url: baseUrl + "/vehicle/delete",
    method: "POST",
    data: data,
  });
}

//删除车辆信息(列表)
export function vehicleDeleteList(data) {
  return request({
    url: baseUrl + "/vehicle/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================仓库================================================
//查询仓库
export function inventorylistPage(data) {
  return request({
    url: baseUrl + "/inventory/list-page",
    method: "get",
    params: data,
  });
}

//新增仓库
export function inventoryAdd(data) {
  return request({
    url: baseUrl + "/inventory/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新仓库
export function inventoryUpdate(data) {
  return request({
    url: baseUrl + "/inventory/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除仓库
export function inventoryDelete(data) {
  return request({
    url: baseUrl + "/inventory/delete",
    method: "POST",
    data: data,
  });
}

//删除仓库(列表)
export function inventoryDeleteList(data) {
  return request({
    url: baseUrl + "/inventory/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================区域================================================
//查询仓库区域
export function districtlistPage(data) {
  return request({
    url: baseUrl + "/district/list-page",
    method: "get",
    params: data,
  });
}

//新增仓库区域
export function districtAdd(data) {
  return request({
    url: baseUrl + "/district/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新仓库区域
export function districtUpdate(data) {
  return request({
    url: baseUrl + "/district/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除仓库区域
export function districtDelete(data) {
  return request({
    url: baseUrl + "/district/delete",
    method: "POST",
    data: data,
  });
}

//删除仓库区域(列表)
export function districtDeleteList(data) {
  return request({
    url: baseUrl + "/district/delete-list",
    method: "POST",
    data: data,
  });
}

// =============================================货位================================================
//查询仓库货位
export function positionlistPage(data) {
  return request({
    url: baseUrl + "/position/list-page",
    method: "get",
    params: data,
  });
}

//新增仓库货位
export function positionAdd(data) {
  return request({
    url: baseUrl + "/position/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新仓库货位
export function positionUpdate(data) {
  return request({
    url: baseUrl + "/position/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除仓库货位
export function positionDelete(data) {
  return request({
    url: baseUrl + "/position/delete",
    method: "POST",
    data: data,
  });
}

//删除仓库货位(列表)
export function positionDeleteList(data) {
  return request({
    url: baseUrl + "/position/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================商品================================================
//查询商品
export function goodslistPage(data) {
  return request({
    url: baseUrl + "/goods/list-page",
    method: "get",
    params: data,
  });
}

//新增商品
export function goodsAdd(data) {
  return request({
    url: baseUrl + "/goods/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新商品
export function goodsUpdate(data) {
  return request({
    url: baseUrl + "/goods/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除商品
export function goodsDelete(data) {
  return request({
    url: baseUrl + "/goods/delete",
    method: "POST",
    data: data,
  });
}

//删除商品(列表)
export function goodsDeleteList(data) {
  return request({
    url: baseUrl + "/goods/delete-list",
    method: "POST",
    data: data,
  });
}

// //查询门店（code）
// export function getByshopCode(data) {
//   return request({
//       url: baseUrl + "/shop/getByshopCode",
//       method: "get",
//       params: data,
//   });
// }

//查询门店
export function shoplistPage(data) {
  return request({
    url: baseUrl + "/shop/list-page",
    method: "get",
    params: data,
  });
}

//新增门店
export function shopAdd(data) {
  return request({
    url: baseUrl + "/shop/add",
    method: "POST",
    data: qs.parse(data),
  });
}

//更新门店
export function shopUpdate(data) {
  return request({
    url: baseUrl + "/shop/update",
    method: "POST",
    data: qs.parse(data),
  });
}

//删除门店
export function shopDelete(data) {
  return request({
    url: baseUrl + "/shop/delete",
    method: "POST",
    data: data,
  });
}

//删除门店(列表)
export function shopDeleteList(data) {
  return request({
    url: baseUrl + "/shop/delete-list",
    method: "POST",
    data: data,
  });
}

//查询业务主体（分页)
export function entityListPage(data) {
  return request({
    url: baseUrl + "/entity/list-page",
    method: "get",
    params: data,
  });
}

//添加业务主体
export function entityAdd(data) {
  return request({
    url: baseUrl + "/entity/add",
    method: "POST",
    data: data,
  });
}

//更新业务主体
export function entityUpdate(data) {
  return request({
    url: baseUrl + "/entity/update",
    method: "POST",
    data: data,
  });
}

//删除业务主体
export function entityDelete(data) {
  return request({
    url: baseUrl + "/entity/delete",
    method: "POST",
    data: data,
  });
}

//删除业务主体(列表)
export function entityDeleteList(data) {
  return request({
    url: baseUrl + "/entity/delete-list",
    method: "POST",
    data: data,
  });
}

//查看供应商-地址信息
export function supplierAddressPage(data) {
  return request({
    url: baseUrl + "/supplierAddress/list-page",
    method: "get",
    params: data,
  });
}

//添加供应商-地址信息
export function supplierAddressAdd(data) {
  return request({
    url: baseUrl + "/supplierAddress/add",
    method: "POST",
    data: data,
  });
}

//更新供应商-地址信息
export function supplierAddressUpdate(data) {
  return request({
    url: baseUrl + "/supplierAddress/update",
    method: "POST",
    data: data,
  });
}

//删除供应商-地址信息
export function supplierAddressDelete(data) {
  return request({
    url: baseUrl + "/supplierAddress/delete",
    method: "POST",
    data: data,
  });
}

//删除供应商-地址信息(列表)
export function addressDeleteList(data) {
  return request({
    url: baseUrl + "/supplierAddress/delete-list",
    method: "POST",
    data: data,
  });
}

//添加供应商-开票信息
export function supplierBillingAdd(data) {
  return request({
    url: baseUrl + "/supplierBilling/add",
    method: "POST",
    data: data,
  });
}

//更新供应商-开票信息
export function supplierBillingUpdate(data) {
  return request({
    url: baseUrl + "/supplierBilling/update",
    method: "POST",
    data: data,
  });
}

//删除供应商-开票信息
export function supplierBillingDelete(data) {
  return request({
    url: baseUrl + "/supplierBilling/delete",
    method: "POST",
    data: data,
  });
}

//删除供应商-开票信息(列表)
export function supplierBillingDeleteList(data) {
  return request({
    url: baseUrl + "/supplierBilling/delete-list",
    method: "POST",
    data: data,
  });
}

// ==============================================一些不分页的数据================================================
//查询客户--不分页
export function CustomerList(data) {
  return request({
    url: baseUrl + "/customer/list",
    method: "get",
    params: data,
  });
}

//查询仓库区域列表（不分页）
export function districtlist(data) {
  return request({
    url: baseUrl + "/district/list",
    method: "get",
    params: data,
  });
}

// 查询仓库（不分页）
export function inventorylist(data) {
  return request({
    url: baseUrl + "/inventory/list",
    method: "get",
    params: data,
  });
}

//查询品牌列表（不分页）
export function brandlist(data) {
  return request({
    url: baseUrl + "/brand/list",
    method: "get",
    data: data,
  });
}

//查询供应商（不分页）
export function Supplierlist(data) {
  return request({
    url: baseUrl + "/supplier/list",
    method: "get",
    params: data,
  });
}

//查询业务主体（不分页)
export function entityList(data) {
  return request({
    url: baseUrl + "/entity/list",
    method: "get",
    params: data,
  });
}

//查询门店（不分页）
export function shoplist(data) {
  return request({
    url: baseUrl + "/shop/list",
    method: "get",
    params: data,
  });
}

//查询商品（不分页）
export function goodslist(data) {
  return request({
    url: baseUrl + "/goods/list",
    method: "get",
    params: data,
  });
}

//查询货位列表（不分页）
export function positionList(data) {
  return request({
    url: baseUrl + "/position/list",
    method: "get",
    params: data,
  });
}

//查询车辆（不分页）
export function vehicleList(data) {
  return request({
    url: baseUrl + "/vehicle/list",
    method: "get",
    params: data,
  });
}