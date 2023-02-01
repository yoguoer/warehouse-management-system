package com.example.api_project.mapper;

import com.example.api_project.vo.PurchaseIntegrate;

import java.util.List;


/**
 * 采购统计
 *
 */
public interface PurchaseIntegrateMapper {

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<PurchaseIntegrate> queryAllByShop(String shopCode,Integer startRows, Integer pageSize);
    List<PurchaseIntegrate> queryAllByGoods(String goodsCode,Integer startRows, Integer pageSize);
    List<PurchaseIntegrate> queryAllBySupplier(String supplierCode,Integer startRows, Integer pageSize);

    Long countByShop(PurchaseIntegrate purchaseIntegrate);
    Long countByGoods(PurchaseIntegrate purchaseIntegrate);
    Long countBySupplier(PurchaseIntegrate purchaseIntegrate);

}

