package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 调货审批表(TransferCheck)实体类
 *
 */
public class TransferCheck implements Serializable {
    private static final Long  serialVersionUID = -93866335830925171L;
    /**
     * 主键
     */
    private String transferCheckKey;
    /**
     * 调入门店
     */
    private String inputShopCode;
    private String inputShopName;

    /**
     * 调出门店
     */
    private String outputShopCode;
    private String outputShopName;

    /**
     * 审批状态
     */
    private Integer checkStatus;
    /**
     * 申请时间
     */
    private String happenTime;
    /**
     * 审批时间
     */
    private String checkTime;
    /**
     * 审批数
     */
    private Integer checkNum;
    /**
     * 审批意见
     */
    private String description;
    /**
     * 调货商品
     */
    private String goodsCode;
    /**
     * 关联入库单
     */
    private String inputWarehouseKey;
    /**
     * 关联出库单
     */
    private String outputWarehouseKey;

    private InputWarehouse inputWarehouse;

    private OutputWarehouse outputWarehouse;

    /**
     * 门店编号
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 计划入库数
     */
    private Integer inputPlan;
    /**
     * 入库价格
     */
    private Float inputPrice;
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
     * 入库类型
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
     * 计划出库数
     */
    private Integer outputPlan;
    /**
     * 出库价格
     */
    private Float outputPrice;
    /**
     * 实际出库数
     */
    private Integer outputActual;
    /**
     * 当前查询类型（0入库、1出库）
     */
    private Integer checkType;

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getTransferCheckKey() {
        return transferCheckKey;
    }

    public void setTransferCheckKey(String transferCheckKey) {
        this.transferCheckKey = transferCheckKey;
    }

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

    public String getOutputShopName() {
        return outputShopName;
    }

    public void setOutputShopName(String outputShopName) {
        this.outputShopName = outputShopName;
    }

    public String getOutputShopCode() {
        return outputShopCode;
    }

    public void setOutputShopCode(String outputShopCode) {
        this.outputShopCode = outputShopCode;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getInputWarehouseKey() {
        return inputWarehouseKey;
    }

    public void setInputWarehouseKey(String inputWarehouseKey) {
        this.inputWarehouseKey = inputWarehouseKey;
    }

    public String getOutputWarehouseKey() {
        return outputWarehouseKey;
    }

    public void setOutputWarehouseKey(String outputWarehouseKey) {
        this.outputWarehouseKey = outputWarehouseKey;
    }

    public InputWarehouse getInputWarehouse() {
        return inputWarehouse;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getInputPlan() {
        return inputPlan;
    }

    public void setInputPlan(Integer inputPlan) {
        this.inputPlan = inputPlan;
    }

    public Float getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(Float inputPrice) {
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

    public Integer getOutputPlan() {
        return outputPlan;
    }

    public void setOutputPlan(Integer outputPlan) {
        this.outputPlan = outputPlan;
    }

    public Float getOutputPrice() {
        return outputPrice;
    }

    public void setOutputPrice(Float outputPrice) {
        this.outputPrice = outputPrice;
    }

    public Integer getOutputActual() {
        return outputActual;
    }

    public void setOutputActual(Integer outputActual) {
        this.outputActual = outputActual;
    }
}

