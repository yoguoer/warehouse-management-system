package com.example.api_project.pojo;

public class Goods {
    private String goodsName;//商品名称
    private String goodsCode;//商品货号
    private String goodsBarcode;//商品条码
    private String createTime;//添加时间
    private String goodsKey;//商品ID
    private String modelCode;//型号
    private Integer state;//0下架 1上架
    private String supplierKey;//供应商
    private String categoryKey;//所属分类
    private String brandCode;//品牌
    //商品头图？
    private String goodsUnit;//商品单位
    private String unitType; //单位类型：BASIC：基本单位， SUPPORT：辅助单位
    private Integer priceRetail;//零售价
    private Integer priceWholesaler;//批发价
    private Integer priceLatestPurchase;//最近进价
    private String unitDescription;//换算关系
    private Integer weight;//重量：克
    private Integer volume;//体积

    private String categoryName;
    private String supplierName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBarcode() {
        return goodsBarcode;
    }

    public void setGoodsBarcode(String goodsBarcode) {
        this.goodsBarcode = goodsBarcode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGoodsKey() {
        return goodsKey;
    }

    public void setGoodsKey(String goodsKey) {
        this.goodsKey = goodsKey;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSupplierKey() {
        return supplierKey;
    }

    public void setSupplierKey(String supplierKey) {
        this.supplierKey = supplierKey;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public Integer getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(Integer priceRetail) {
        this.priceRetail = priceRetail;
    }

    public Integer getPriceWholesaler() {
        return priceWholesaler;
    }

    public void setPriceWholesaler(Integer priceWholesaler) {
        this.priceWholesaler = priceWholesaler;
    }

    public Integer getPriceLatestPurchase() {
        return priceLatestPurchase;
    }

    public void setPriceLatestPurchase(Integer priceLatestPurchase) {
        this.priceLatestPurchase = priceLatestPurchase;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}