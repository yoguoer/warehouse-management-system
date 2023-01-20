package com.example.api_project.service;

import com.example.api_project.vo.PurchaseIntegrate;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 销售统计
 *
 */
public interface PurchaseIntegrateService {

    /**
     * 分页查询
     *
     * @param purchaseIntegrate 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(PurchaseIntegrate purchaseIntegrate, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);


}
