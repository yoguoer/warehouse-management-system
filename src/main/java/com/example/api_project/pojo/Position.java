package com.example.api_project.pojo;

public class Position {
    private String inventoryDistrictkey;//所属区域
    private String inventoryKey;//所属仓库
    private String positionCode; //货位编号
    private String positionType; //货位类型：store：存储位 assemble: 集货位
    private String maxWeight; //最大重量
    private String maxCapacity; //最大容量(m3)
    private String description; //备注
    private String positionKey;

    public String getInventoryDistrictkey() {
        return inventoryDistrictkey;
    }

    public void setInventoryDistrictkey(String inventoryDistrictkey) {
        this.inventoryDistrictkey = inventoryDistrictkey;
    }

    public String getInventoryKey() {
        return inventoryKey;
    }

    public void setInventoryKey(String inventoryKey) {
        this.inventoryKey = inventoryKey;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPositionKey() {
        return positionKey;
    }

    public void setPositionKey(String positionKey) {
        this.positionKey = positionKey;
    }
}
