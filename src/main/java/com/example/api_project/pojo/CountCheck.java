package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 盘亏盘盈审批表(CountCheck)实体类
 *
 */
public class CountCheck implements Serializable {
    private static final long serialVersionUID = -90640988250509663L;
    /**
     * 主键
     */
    private String countCheckKey;
    /**
     * 申请时间
     */
    private String happenTime;
    /**
     * 关联库存
     */
    private String shopkeeperWarehouseKey;
    /**
     * 审批状态
     */
    private Integer checkStatus;
    /**
     * 审批时间
     */
    private String checkTime;
    /**
     * 审批类型(0盘盈，1盘亏)
     */
    private Integer checkType;
    /**
     * 审批数
     */
    private Integer checkNum;
    /**
     * 审批意见
     */
    private String description;
    /**
     * 审批人
     */
    private String checkPeople;


    public String getCountCheckKey() {
        return countCheckKey;
    }

    public void setCountCheckKey(String countCheckKey) {
        this.countCheckKey = countCheckKey;
    }

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public String getShopkeeperWarehouseKey() {
        return shopkeeperWarehouseKey;
    }

    public void setShopkeeperWarehouseKey(String shopkeeperWarehouseKey) {
        this.shopkeeperWarehouseKey = shopkeeperWarehouseKey;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
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

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }

}

