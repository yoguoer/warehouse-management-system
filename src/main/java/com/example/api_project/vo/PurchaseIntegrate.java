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
