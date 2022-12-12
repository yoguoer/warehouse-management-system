package com.example.api_project.pojo;

public class District {
    private String description;//备注
    private String districtCode;//区域号
    private String inventoryDistrictkey;//区域key
    private String inventoryKey;//所属仓库
    private String sort;//排序

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
