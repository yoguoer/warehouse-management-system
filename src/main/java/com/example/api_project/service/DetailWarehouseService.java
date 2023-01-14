package com.example.api_project.service;

import com.example.api_project.pojo.DetailWarehouse;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 出入库明细表(DetailWarehouse)表服务接口
 *
 */
public interface DetailWarehouseService {

    /**
     * 通过ID查询单条数据
     *
     * @param detailWarehouseKey 主键
     * @return 实例对象
     */
    DetailWarehouse queryById(String detailWarehouseKey);

    /**
     * 分页查询
     *
     * @param detailWarehouse 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(DetailWarehouse detailWarehouse, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param detailWarehouse 实例对象
     * @return 实例对象
     */
    DetailWarehouse insert(DetailWarehouse detailWarehouse);

    /**
     * 修改数据
     *
     * @param detailWarehouse 实例对象
     * @return 实例对象
     */
    DetailWarehouse update(DetailWarehouse detailWarehouse);

    /**
     * 通过主键删除数据
     *
     * @param detailWarehouse
     * @return 是否成功
     */
    boolean deleteById(DetailWarehouse detailWarehouse);

}
