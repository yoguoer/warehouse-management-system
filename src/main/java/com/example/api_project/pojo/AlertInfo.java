package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 预警信息表(AlertInfo)实体类
 *
 */
public class AlertInfo implements Serializable {
    private static final long serialVersionUID = -33151769048072246L;
    
    private String alertInfoKey;
    /**
     * 预警对象
     */
    private String shopSupplierKey;
    /**
     * 预警商品
     */
    private String goodsKey;
    /**
     * 状态（0：未处理，1：已处理）
     */
    private Integer status;
    /**
     * 预警时间
     */
    private String alertTime;
    /**
     * 预警类型（0：连续未动销预警，1：满仓预警，2：缺货预警）
     */
    private Integer type;


    public String getAlertInfoKey() {
        return alertInfoKey;
    }

    public void setAlertInfoKey(String alertInfoKey) {
        this.alertInfoKey = alertInfoKey;
    }

    public String getShopSupplierKey() {
        return shopSupplierKey;
    }

    public void setShopSupplierKey(String shopSupplierKey) {
        this.shopSupplierKey = shopSupplierKey;
    }

    public String getGoodsKey() {
        return goodsKey;
    }

    public void setGoodsKey(String goodsKey) {
        this.goodsKey = goodsKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

