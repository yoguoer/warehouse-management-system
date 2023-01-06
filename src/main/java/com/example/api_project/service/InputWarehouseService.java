package com.example.api_project.service;

import com.example.api_project.pojo.InputWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 入库表(InputWarehouse)表服务接口
 *

 */
public interface InputWarehouseService {

    /**
     * 不分页查询
     */
    List<InputWarehouse> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @param inputWarehouseKey 主键
     * @return 实例对象
     */
    InputWarehouse queryById(String inputWarehouseKey);

    /**
     * 分页查询
     *
     * @param inputWarehouse 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(InputWarehouse inputWarehouse, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param inputWarehouse 实例对象
     * @return 实例对象
     */
    InputWarehouse insert(InputWarehouse inputWarehouse);

    /**
     * 修改数据
     *
     * @param inputWarehouse 实例对象
     * @return 实例对象
     */
    InputWarehouse update(InputWarehouse inputWarehouse);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(InputWarehouse inputWarehouse);

}
