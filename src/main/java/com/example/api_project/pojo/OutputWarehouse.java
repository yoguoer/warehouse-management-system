package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 出库表(OutputWarehouse)实体类
 *
 */
public class OutputWarehouse implements Serializable {
    private static final long serialVersionUID = -96250854834159520L;
    /**
     * 主键
     */
    private String outputWarehouseKey;
    /**
     * 门店编码
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 计划出库数
     */
    private Integer outputPlan;
    /**
     * 出库价格
     */
    private Long outputPrice;
    /**
     * 实际出库数
     */
    private Integer outputActual;
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
     * 状态
     */
    private Integer status;
    /**
     * 出库类型（零售出库、大客户出库、调货出库）
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
     * 退货原因
     */
    private String returnReason;
    /**
     * 车辆编码
     */
    private String vehicleCode;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 退货数
     */
    private Integer returnNum;

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

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getOutputWarehouseKey() {
        return outputWarehouseKey;
    }

    public void setOutputWarehouseKey(String outputWarehouseKey) {
        this.outputWarehouseKey = outputWarehouseKey;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getOutputPlan() {
        return outputPlan;
    }

    public void setOutputPlan(Integer outputPlan) {
        this.outputPlan = outputPlan;
    }

    public Long getOutputPrice() {
        return outputPrice;
    }

    public void setOutputPrice(Long outputPrice) {
        this.outputPrice = outputPrice;
    }

    public Integer getOutputActual() {
        return outputActual;
    }

    public void setOutputActual(Integer outputActual) {
        this.outputActual = outputActual;
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

