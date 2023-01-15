package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 出入库明细表(DetailWarehouse)实体类
 *
 */
public class DetailWarehouse implements Serializable {
    private static final long serialVersionUID = -57430741243052992L;
    /**
     * 主键
     */
    private String detailWarehouseKey;
    /**
     * 订单编号
     */
    private String inputOutputKey;
    /**
     * 变化类型(0：入库/总量增加，1：出库/总量减少)
     */
    private Integer type;
    /**
     * 变化数量
     */
    private Integer quantity;
    /**
     * 交易类型(0采购入库、1采购退货出库、2零售出库、3零售退货入库、4客户订购出库、5客户订购退货入库、6调货入库、7调货出库)
     */
    private Integer transType;
    /**
     * 发生日期
     */
    private String atTime;
    /**
     * 起始库存
     */
    private Integer startNum;
    /**
     * 结余库存
     */
    private Integer finalNum;
    /**
     * 关联库存
     */
    private String shopkeeperWarehouseKey;
    private String shopCode;//门店编码
    private String goodsCode;//商品编码

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDetailWarehouseKey() {
        return detailWarehouseKey;
    }

    public void setDetailWarehouseKey(String detailWarehouseKey) {
        this.detailWarehouseKey = detailWarehouseKey;
    }

    public String getInputOutputKey() {
        return inputOutputKey;
    }

    public void setInputOutputKey(String inputOutputKey) {
        this.inputOutputKey = inputOutputKey;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getFinalNum() {
        return finalNum;
    }

    public void setFinalNum(Integer finalNum) {
        this.finalNum = finalNum;
    }

    public String getShopkeeperWarehouseKey() {
        return shopkeeperWarehouseKey;
    }

    public void setShopkeeperWarehouseKey(String shopkeeperWarehouseKey) {
        this.shopkeeperWarehouseKey = shopkeeperWarehouseKey;
    }

}

