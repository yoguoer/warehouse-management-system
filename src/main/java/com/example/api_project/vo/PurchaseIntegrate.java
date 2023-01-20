package com.example.api_project.vo;

//采购统计
public class PurchaseIntegrate {
    String shopCode;//门店编码
    String shopName;//门店名称
    String goodsCode;//商品编码
    String goodsName;//商品名称
    Integer inOrderNum;//在单数
    Integer productNum;//生产数
    Integer onWayNum;//在途数
    Integer inStoreNum;//入库数
    Integer status;//订单状态
    /**
     * 供应商编码
     */
    private String supplierCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 计划入库数
     */
    private Integer inputPlan;
    /**
     * 入库价格
     */
    private Long inputPrice;
    /**
     * 实际入库数
     */
    private Integer inputActual;
    /**
     * 退货数
     */
    private Integer returnNum;
    /**
     * 入库类型(采购入库、调货入库)
     */
    private Integer type;
    private Integer inOrderSum;
    private Integer productSum;
    private Integer onWaySum;
    private Integer inStoreSum;
    private Integer returnSum;
    private Integer returnCount;

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getInOrderSum() {
        return inOrderSum;
    }

    public void setInOrderSum(Integer inOrderSum) {
        this.inOrderSum = inOrderSum;
    }

    public Integer getProductSum() {
        return productSum;
    }

    public void setProductSum(Integer productSum) {
        this.productSum = productSum;
    }

    public Integer getOnWaySum() {
        return onWaySum;
    }

    public void setOnWaySum(Integer onWaySum) {
        this.onWaySum = onWaySum;
    }

    public Integer getInStoreSum() {
        return inStoreSum;
    }

    public void setInStoreSum(Integer inStoreSum) {
        this.inStoreSum = inStoreSum;
    }

    public Integer getReturnSum() {
        return returnSum;
    }

    public void setReturnSum(Integer returnSum) {
        this.returnSum = returnSum;
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

    public Integer getInputPlan() {
        return inputPlan;
    }

    public void setInputPlan(Integer inputPlan) {
        this.inputPlan = inputPlan;
    }

    public Long getInputPrice() {
        return inputPrice;
    }

    public void setInputPrice(Long inputPrice) {
        this.inputPrice = inputPrice;
    }

    public Integer getInputActual() {
        return inputActual;
    }

    public void setInputActual(Integer inputActual) {
        this.inputActual = inputActual;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getInOrderNum() {
        return inOrderNum;
    }

    public void setInOrderNum(Integer inOrderNum) {
        this.inOrderNum = inOrderNum;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getOnWayNum() {
        return onWayNum;
    }

    public void setOnWayNum(Integer onWayNum) {
        this.onWayNum = onWayNum;
    }

    public Integer getInStoreNum() {
        return inStoreNum;
    }

    public void setInStoreNum(Integer inStoreNum) {
        this.inStoreNum = inStoreNum;
    }
}
