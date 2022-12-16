package com.example.api_project.mapper;

import com.example.api_project.vo.SupplierInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商的开票信息对应表(SupplierInventory)表数据库访问层
 *
 */
public interface SupplierInventoryMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<SupplierInventory> getList();


    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<SupplierInventory> queryAllByLimit(Integer status, String supplierKey,String supplierName,String inventoryKey,String inventoryName,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param SupplierInventory 查询条件
     * @return 总行数
     */
    long count(SupplierInventory SupplierInventory);

    /**
     * 修改数据
     *
     * @param SupplierInventory 实例对象
     * @return 影响行数
     */
    int update(SupplierInventory SupplierInventory);

    /**
     * 通过主键删除数据
     *
     * @param SupplierInventory
     * @return 影响行数
     */
    int deleteById(SupplierInventory SupplierInventory);

}

