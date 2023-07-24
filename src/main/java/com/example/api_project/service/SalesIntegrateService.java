package com.example.api_project.service;

import com.example.api_project.vo.SalesIntegrate;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 销售统计
 *
 */
public interface SalesIntegrateService {

    /**
     * 分页查询
     *
     * @param salesIntegrate 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(SalesIntegrate salesIntegrate, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

}
