package com.example.api_project.pojo;

public class District {
    private String description;//备注
    private String districtCode;//区域号
    private String inventoryDistrictkey;//区域key
    private String inventoryKey;//所属仓库
    private Integer sort;//排序
    private Integer status;//状态
    private String districtName;//区域名称
    private String inventoryCode; //仓库编号
    private String inventoryName; //仓库名

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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
