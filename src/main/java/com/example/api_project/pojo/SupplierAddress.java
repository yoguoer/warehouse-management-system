package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 供应商的地址表(SupplierAddress)实体类
 *
 */
public class SupplierAddress implements Serializable {
    private static final long serialVersionUID = -43812140793386705L;
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
     * 具体地址
     */
    private String detail;
    /**
     * 地址类型
     */
    private String addressType;
    /**
     * 地址对应的供应商编号
     */
    private String supplierAddressKey;
    private String supplierName;//供应商名称
    private String supplierCode;//供应商编号
    /**
     * 地址本身的key
     */
    private String addressKey;


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

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getSupplierAddressKey() {
        return supplierAddressKey;
    }

    public void setSupplierAddressKey(String supplierAddressKey) {
        this.supplierAddressKey = supplierAddressKey;
    }

    public String getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

}

