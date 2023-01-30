package com.example.api_project.mapper;

import com.example.api_project.vo.TransferIntegrate;

import java.util.List;


/**
 * 销售统计
 *
 */
public interface TransferIntegrateMapper {

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TransferIntegrate> queryAllByShopIn(String shopCode, Integer startRows, Integer pageSize);
    List<TransferIntegrate> queryAllByShopOut(String shopCode, Integer startRows, Integer pageSize);
    List<TransferIntegrate> queryAllByGoods(String goodsCode,String InShopCode, String OutShopCode,Integer startRows, Integer pageSize);

}

