package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 入库表(InputWarehouse)实体类
 *
 */
public class InputWarehouse implements Serializable {
    private static final long serialVersionUID = 187245813833868391L;
    /**
     * 主键
     */
    private String inputWarehouseKey;
    /**
     * 门店编号
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 计划入库数
     */
    private Integer inputPlan;
    /**
     * 入库价格
     */
    private Long inputPrice;
    /**
     * 实际入库数
     */
    private Integer inputActual;
    /**
     * 仓库编码
     */
    private String inventoryCode;
    /**
     * 货位编码
     */
    private String positionCode;
    /**
     * 预计日期
     */
    private String createTime;
    /**
     * 最迟日期
     */
    private String deadlineTime;
    /**
     * 车辆编码
     */
    private String vehicleCode;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 入库类型(采购入库、调货入库)
     */
    private Integer type;
    /**
     * 门店操作员编码
     */
    private String shopPeopleCode;
    /**
     * 仓库操作员编码
     */
    private String inventoryPeopleCode;
    /**
     * 调货门店编码
     */
    private String inputShopCode;
    /**
     * 调货门店编名称
     */
    private String inputShopName;
    /**
     * 退货原因
     */
    private String returnReason;
    /**
     * 退货数
     */
    private Integer returnNum;

    public String getInputShopCode() {
        return inputShopCode;
    }

    public void setInputShopCode(String inputShopCode) {
        this.inputShopCode = inputShopCode;
    }

    public String getInputShopName() {
        return inputShopName;
    }

    public void setInputShopName(String inputShopName) {
        this.inputShopName = inputShopName;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getInputWarehouseKey() {
        return inputWarehouseKey;
    }

    public void setInputWarehouseKey(String inputWarehouseKey) {
        this.inputWarehouseKey = inputWarehouseKey;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getInputPlan() {
        return inputPlan;
    }

    public void setInputPlan(Integer inputPlan) {
        this.inputPlan = inputPlan;
    }

    public Long getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(Long inputPrice) {
        this.inputPrice = inputPrice;
    }

    public Integer getInputActual() {
        return inputActual;
    }

    public void setInputActual(Integer inputActual) {
        this.inputActual = inputActual;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getShopPeopleCode() {
        return shopPeopleCode;
    }

    public void setShopPeopleCode(String shopPeopleCode) {
        this.shopPeopleCode = shopPeopleCode;
    }

    public String getInventoryPeopleCode() {
        return inventoryPeopleCode;
    }

    public void setInventoryPeopleCode(String inventoryPeopleCode) {
        this.inventoryPeopleCode = inventoryPeopleCode;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

}

