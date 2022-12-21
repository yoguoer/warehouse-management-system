package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 门店表(Shop)实体类
 *
 */
public class Shop implements Serializable {
    private static final long serialVersionUID = 669958939177561911L;
    /**
     * 门店key
     */
    private String shopKey;
    /**
     * 门店编号
     */
    private String shopCode;
    /**
     * 门店名称
     */
    private String shopName;
    /**
     * 业务人员
     */
    private String businessPersonCode;
    /**
     * 责任人员
     */
    private String liablePersonCode;
    /**
     * 门店状态
     */
    private Integer shopStatus;
    /**
     * 所属分类key
     */
    private String categoryKey;
    /**
     * 备注
     */
    private String description;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 详细地址
     */
    private String detail;

    private Integer cooperationType;

    public Integer getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(Integer cooperationType) {
        this.cooperationType = cooperationType;
    }

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
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

    public String getBusinessPersonCode() {
        return businessPersonCode;
    }

    public void setBusinessPersonCode(String businessPersonCode) {
        this.businessPersonCode = businessPersonCode;
    }

    public String getLiablePersonCode() {
        return liablePersonCode;
    }

    public void setLiablePersonCode(String liablePersonCode) {
        this.liablePersonCode = liablePersonCode;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}

