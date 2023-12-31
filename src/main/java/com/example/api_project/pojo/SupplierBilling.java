package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 供应商的开票信息对应表(SupplierBilling)实体类
 *
 */
public class SupplierBilling implements Serializable {
    private static final long serialVersionUID = -88365776600767095L;
    /**
     * 与供应商对应的code
     */
    private String supplierBillingKey;
    private String supplierName;//供应商名称
    private String supplierCode;//供应商编号
    /**
     * 开票信息本身的key
     */
    private String billingKey;
    /**
     * 税号
     */
    private String taxNumber;
    /**
     * 账户名
     */
    private String accountName;
    /**
     * 银行账号
     */
    private String accountNumber;
    /**
     * 开户银行全称
     */
    private String bankName;
    /**
     * 该账户负责人联系电话
     */
    private String accountTel;

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

    public String getSupplierBillingKey() {
        return supplierBillingKey;
    }

    public void setSupplierBillingKey(String supplierBillingKey) {
        this.supplierBillingKey = supplierBillingKey;
    }

    public String getBillingKey() {
        return billingKey;
    }

    public void setBillingKey(String billingKey) {
        this.billingKey = billingKey;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountTel() {
        return accountTel;
    }

    public void setAccountTel(String accountTel) {
        this.accountTel = accountTel;
    }

}

