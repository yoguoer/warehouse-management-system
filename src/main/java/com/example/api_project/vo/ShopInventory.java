package com.example.api_project.vo;

public class ShopInventory {
    private String inventoryKey; //仓库key
    private String inventoryCode; //仓库编号
    private String inventoryName; //仓库名
    private String shopKey;//零售商key
    private String shopCode;//零售商编号
    private String shopName;//零售商名称
    private String description; //备注
    private Integer status; //状态
    private String belongKey;//归属于（仓库和零售商之间的连接key）

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
