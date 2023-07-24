package com.example.api_project.pojo;

import java.util.List;

public class Supplier {
    private String supplierCode;//供应商编号
    private String supplierName;//供应商名称
    private String categoryKey;//所属分类
    private String supplierKey;//供应商key
    private String superlierDescription;//备注

    public List<SupplierBilling> supplierBillingList;//开票信息
    public List<SupplierAddress> supplierAddressList;//联系地址
    public List<Contact> supplierContactList;//联系人
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getSupplierKey() {
        return supplierKey;
    }

    public void setSupplierKey(String supplierKey) {
        this.supplierKey = supplierKey;
    }

    public String getSuperlierDescription() {
        return superlierDescription;
    }

    public void setSuperlierDescription(String superlierDescription) {
        this.superlierDescription = superlierDescription;
    }

    public List<SupplierBilling> getSupplierBillingList() {
        return supplierBillingList;
    }

    public void setSupplierBillingList(List<SupplierBilling> supplierBillingList) {
        this.supplierBillingList = supplierBillingList;
    }

    public List<SupplierAddress> getSupplierAddressList() {
        return supplierAddressList;
    }

    public void setSupplierAddressList(List<SupplierAddress> supplierAddressList) {
        this.supplierAddressList = supplierAddressList;
    }

    public List<Contact> getSupplierContactList() {
        return supplierContactList;
    }

    public void setSupplierContactList(List<Contact> supplierContactList) {
        this.supplierContactList = supplierContactList;
    }
}