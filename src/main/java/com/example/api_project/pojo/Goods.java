package com.example.api_project.pojo;

public class Goods {
    private String goodsName;//商品名称
    private String goodsCode;//商品货号
    private String goodsBarcode;//商品条码
    private String createTime;//添加时间
    private String goodsKey;//商品ID
    private String modelCode;//型号
    private String state;//0下架 1上架
    private String supplierCode;//供应商
    private String categoryKey;//所属分类
    private String brandCode;//品牌
    private String inventoryCode;//默认仓库
    //商品头图？
    private String goodsUnit;//商品单位
    private String unitType; //单位类型：BASIC：基本单位， SUPPORT：辅助单位
    private String inventoryBook;//账面库存
    private String priceRetail;//零售价
    private String priceWholesaler;//批发价
    private String priceCostAverage;//成本均价
    private String priceCostPreset;//预设进价
    private String priceCostReference;//参考成本
    private String priceLatestPurchase;//最近进价
    private String unitDescription;//换算关系
    private String weight;//重量：克
    private String volume;//体积

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getsupplierCode() {
        return supplierCode;
    }

    public void setsupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getinventoryCode() {
        return inventoryCode;
    }

    public void setinventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
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

    public String getInventoryBook() {
        return inventoryBook;
    }

    public void setInventoryBook(String inventoryBook) {
        this.inventoryBook = inventoryBook;
    }

    public String getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(String priceRetail) {
        this.priceRetail = priceRetail;
    }

    public String getPriceWholesaler() {
        return priceWholesaler;
    }

    public void setPriceWholesaler(String priceWholesaler) {
        this.priceWholesaler = priceWholesaler;
    }

    public String getPriceCostAverage() {
        return priceCostAverage;
    }

    public void setPriceCostAverage(String priceCostAverage) {
        this.priceCostAverage = priceCostAverage;
    }

    public String getPriceCostPreset() {
        return priceCostPreset;
    }

    public void setPriceCostPreset(String priceCostPreset) {
        this.priceCostPreset = priceCostPreset;
    }

    public String getPriceCostReference() {
        return priceCostReference;
    }

    public void setPriceCostReference(String priceCostReference) {
        this.priceCostReference = priceCostReference;
    }

    public String getPriceLatestPurchase() {
        return priceLatestPurchase;
    }

    public void setPriceLatestPurchase(String priceLatestPurchase) {
        this.priceLatestPurchase = priceLatestPurchase;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}