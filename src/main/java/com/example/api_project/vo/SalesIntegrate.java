package com.example.api_project.vo;

//销售统计
public class SalesIntegrate {
    String shopCode;//门店编码
    String shopName;//门店名称
    String goodsCode;//商品编码
    String goodsName;//商品名称
    Integer occupyNum;//占用数
    Integer outputNum;//发货数
    Integer status;//订单状态
    /**
     * 退货数
     */
    private Integer returnNum;
    /**
     * 计划出库数
     */
    private Integer outputPlan;
    /**
     * 出库价格
     */
    private Long outputPrice;
    /**
     * 实际出库数
     */
    private Integer outputActual;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 出库类型（零售出库、大客户出库、调货出库）
     */
    private Integer type;
    private Integer occupySum;
    private Integer outputSum;
    private Integer returnSum;
    private Integer returnCount;

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getOccupySum() {
        return occupySum;
    }

    public void setOccupySum(Integer occupySum) {
        this.occupySum = occupySum;
    }

    public Integer getOutputSum() {
        return outputSum;
    }

    public void setOutputSum(Integer outputSum) {
        this.outputSum = outputSum;
    }

    public Integer getReturnSum() {
        return returnSum;
    }

    public void setReturnSum(Integer returnSum) {
        this.returnSum = returnSum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    public Integer getOutputPlan() {
        return outputPlan;
    }

    public void setOutputPlan(Integer outputPlan) {
        this.outputPlan = outputPlan;
    }

    public Long getOutputPrice() {
        return outputPrice;
    }

    public void setOutputPrice(Long outputPrice) {
        this.outputPrice = outputPrice;
    }

    public Integer getOutputActual() {
        return outputActual;
    }

    public void setOutputActual(Integer outputActual) {
        this.outputActual = outputActual;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public Integer getOccupyNum() {
        return occupyNum;
    }

    public void setOccupyNum(Integer occupyNum) {
        this.occupyNum = occupyNum;
    }

    public Integer getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(Integer outputNum) {
        this.outputNum = outputNum;
    }
}
