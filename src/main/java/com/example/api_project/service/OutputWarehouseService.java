package com.example.api_project.service;

import com.example.api_project.pojo.OutputWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出库表(OutputWarehouse)表服务接口
 *
 */
public interface OutputWarehouseService {

    /**
     * 不分页查询
     */
    List<OutputWarehouse> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @param outputWarehouseKey 主键
     * @return 实例对象
     */
    OutputWarehouse queryById(String outputWarehouseKey);

    /**
     * 分页查询
     *
     * @param outputWarehouse 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(OutputWarehouse outputWarehouse, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param outputWarehouse 实例对象
     * @return 实例对象
     */
    OutputWarehouse insert(OutputWarehouse outputWarehouse);

    /**
     * 修改数据
     *
     * @param outputWarehouse 实例对象
     * @return 实例对象
     */
    OutputWarehouse update(OutputWarehouse outputWarehouse);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(OutputWarehouse outputWarehouse);

}
