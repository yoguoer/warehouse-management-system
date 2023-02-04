package com.example.api_project.pojo;

public class Position {
    private String inventoryDistrictkey;//所属区域
    private String inventoryKey;//所属仓库
    private String positionCode; //货位编号
    private String positionType; //货位类型：store：存储位 assemble: 集货位
    private Integer maxWeight; //最大重量
    private Integer maxCapacity; //最大容量(m3)
    private String description; //备注
    private String positionKey;
    private String districtCode;//区域号
    private String districtName;//区域名称
    private String inventoryCode; //仓库编号
    private String inventoryName; //仓库名

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    public Integer getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Integer maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
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
