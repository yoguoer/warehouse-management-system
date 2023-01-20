package com.example.api_project.mapper;

import com.example.api_project.vo.SalesIntegrate;

import java.util.List;


/**
 * 销售统计
 *
 */
public interface SalesIntegrateMapper {

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<SalesIntegrate> queryAllByShop(String shopCode,Integer startRows, Integer pageSize);
    List<SalesIntegrate> queryAllByGoods(String goodsCode, Integer startRows, Integer pageSize);
    List<SalesIntegrate> queryAllByCustomer(String customerCode, Integer startRows, Integer pageSize);

}

