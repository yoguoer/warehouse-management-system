package com.example.api_project.mapper;

import com.example.api_project.vo.ShopInventory;

import java.util.List;

/**
 * 供应商的开票信息对应表(ShopInventory)表数据库访问层
 *
 */
public interface ShopInventoryMapper {

    /**
     * 通过(shopKey)查询单条数据
     *
     * @param shopCode
     * @return 实例对象
     */
    List<ShopInventory> getByshopCode(String shopCode);

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<ShopInventory> getList(String shopCode);


    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<ShopInventory> queryAllByLimit(Integer status, String shopKey,String shopName,String inventoryKey,String inventoryName,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param ShopInventory 查询条件
     * @return 总行数
     */
    long count(ShopInventory ShopInventory);

    /**
     * 修改数据
     *
     * @param ShopInventory 实例对象
     * @return 影响行数
     */
    int update(ShopInventory ShopInventory);

    /**
     * 通过主键删除数据
     *
     * @param ShopInventory
     * @return 影响行数
     */
    int deleteById(ShopInventory ShopInventory);

}

