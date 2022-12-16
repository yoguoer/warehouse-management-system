package com.example.api_project.vo;

public class SupplierInventory {
    private String inventoryKey; //仓库key
    private String inventoryCode; //仓库编号
    private String inventoryName; //仓库名
    private String supplierKey;//供应商key
    private String supplierCode;//供应商编号
    private String supplierName;//供应商名称
    private String description; //备注
    private Integer status; //状态
    private String belongKey;//归属于（仓库和供应商之间的连接key）

    public String getInventoryKey() {
        return inventoryKey;
    }

    public void setInventoryKey(String inventoryKey) {
        this.inventoryKey = inventoryKey;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public void setInventoryName(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public String getSupplierKey() {
        return supplierKey;
    }

    public void setSupplierKey(String supplierKey) {
        this.supplierKey = supplierKey;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBelongKey() {
        return belongKey;
    }

    public void setBelongKey(String belongKey) {
        this.belongKey = belongKey;
    }
}
