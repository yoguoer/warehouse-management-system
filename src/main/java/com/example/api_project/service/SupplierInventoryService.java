package com.example.api_project.service;

import com.example.api_project.vo.SupplierInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商仓库配置对应表(SupplierInventory)表服务接口
 *
 */
public interface SupplierInventoryService {

    /**
     * 不分页查询
     */
    List<SupplierInventory> querylist();

    /**
     * 分页查询
     *
     * @param SupplierInventory 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(SupplierInventory SupplierInventory, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);


    /**
     * 修改数据
     *
     * @param SupplierInventory 实例对象
     * @return 实例对象
     */
    int update(SupplierInventory SupplierInventory);

    /**
     * 通过主键删除数据
     *
     * @param SupplierInventory
     * @return 是否成功
     */
    boolean deleteById(SupplierInventory SupplierInventory);

}
