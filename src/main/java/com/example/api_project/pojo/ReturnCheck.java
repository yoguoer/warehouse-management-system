package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 退货审批表(ReturnCheck)实体类
 *
 */
public class ReturnCheck implements Serializable {
    private static final long serialVersionUID = 195442094154610015L;
    /**
     * 主键
     */
    private String returnCheckKey;
    /**
     * 订单编码
     */
    private String inputOutputKey;
    /**
     * 审批状态
     */
    private Integer checkStatus;

    private String happenTime;
    private String checkTime;
    /**
     * 说明
     */
    private String description;

    private String inputWarehouseKey;

    private String outputWarehouseKey;
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
    private Integer checkNum;
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
     * 入库类型(正常入库、退货入库)
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
     * 退货入库原因
     */
    private String returnReason;
    /**
     * 退货数
     */
    private Integer returnNum;
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
    private Integer checkType;

    private InputWarehouse inputWarehouse;

    private OutputWarehouse outputWarehouse;

    public InputWarehouse getInputWarehouse() {
        return inputWarehouse;
    }

    public String getOutputWarehouseKey() {
        return outputWarehouseKey;
    }

    public void setOutputWarehouseKey(String outputWarehouseKey) {
        this.outputWarehouseKey = outputWarehouseKey;
    }

    public void setInputWarehouse(InputWarehouse inputWarehouse) {
        this.inputWarehouse = inputWarehouse;
    }

    public OutputWarehouse getOutputWarehouse() {
        return outputWarehouse;
    }

    public void setOutputWarehouse(OutputWarehouse outputWarehouse) {
        this.outputWarehouse = outputWarehouse;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getInputWarehouseKey() {
        return inputWarehouseKey;
    }

    public void setInputWarehouseKey(String inputWarehouseKey) {
        this.inputWarehouseKey = inputWarehouseKey;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
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

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
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

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getReturnCheckKey() {
        return returnCheckKey;
    }

    public void setReturnCheckKey(String returnCheckKey) {
        this.returnCheckKey = returnCheckKey;
    }

    public String getInputOutputKey() {
        return inputOutputKey;
    }

    public void setInputOutputKey(String inputOutputKey) {
        this.inputOutputKey = inputOutputKey;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

