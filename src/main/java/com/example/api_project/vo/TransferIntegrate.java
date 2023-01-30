package com.example.api_project.vo;

//销售统计
public class TransferIntegrate {
    String InShopCode;//调入门店编码
    String InShopName;//调入门店名称
    String OutShopCode;//调出门店编码
    String OutShopName;//调出门店名称
    String goodsCode;//商品编码
    String goodsName;//商品名称
    Integer transferNum;//调货单数
    Integer transferSum;//调货商品数
    Integer type;//调货类型（0调入，1调出）
    Integer checkStatus;//状态（0未审核，1同意，2驳回）

    public String getInShopCode() {
        return InShopCode;
    }

    public void setInShopCode(String inShopCode) {
        InShopCode = inShopCode;
    }

    public String getInShopName() {
        return InShopName;
    }

    public void setInShopName(String inShopName) {
        InShopName = inShopName;
    }

    public String getOutShopCode() {
        return OutShopCode;
    }

    public void setOutShopCode(String outShopCode) {
        OutShopCode = outShopCode;
    }

    public String getOutShopName() {
        return OutShopName;
    }

    public void setOutShopName(String outShopName) {
        OutShopName = outShopName;
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

    public Integer getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(Integer transferNum) {
        this.transferNum = transferNum;
    }

    public Integer getTransferSum() {
        return transferSum;
    }

    public void setTransferSum(Integer transferSum) {
        this.transferSum = transferSum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }
}
