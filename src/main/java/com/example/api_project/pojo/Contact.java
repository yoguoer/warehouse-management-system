package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 联系人(Contact)实体类
 *
 */
public class Contact implements Serializable {
    private static final long serialVersionUID = 539822669579253923L;
    /**
     * 联系人名称
     */
    private String contactName;
    /**
     * 联系人key
     */
    private String contactKey;
    /**
     * 联系人电话
     */
    private String contactTel;
    /**
     * 联系人邮箱
     */
    private String contactEmail;
    /**
     * 省
     */
    private String province;
    /**
     * 联系人所属客户的key
     */
    private String contactCustomerKey;
    /**
     * 供应商所属供应商的key
     */
    private String contactSupplierKey;
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
    /**
     * 联系人编号
     */
    private String contactCode;
    private String customerName;//客户名称
    private String customerCode;//客户编号
    private String supplierCode;//供应商编号
    private String supplierName;//供应商名称

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public String getContactSupplierKey() {
        return contactSupplierKey;
    }

    public void setContactSupplierKey(String contactSupplierKey) {
        this.contactSupplierKey = contactSupplierKey;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactKey() {
        return contactKey;
    }

    public void setContactKey(String contactKey) {
        this.contactKey = contactKey;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getContactCustomerKey() {
        return contactCustomerKey;
    }

    public void setContactCustomerKey(String contactCustomerKey) {
        this.contactCustomerKey = contactCustomerKey;
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

    public String getContactCode() {
        return contactCode;
    }

    public void setContactCode(String contactCode) {
        this.contactCode = contactCode;
    }

}

