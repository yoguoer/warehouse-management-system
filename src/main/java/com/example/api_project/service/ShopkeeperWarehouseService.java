package com.example.api_project.service;

import com.example.api_project.pojo.ShopkeeperWarehouse;

import java.util.List;
import java.util.Map;

/**
 * 零售商商品库存表(ShopkeeperWarehouse)表服务接口
 *
 */
public interface ShopkeeperWarehouseService {

    /**
     * 不分页查询
     */
    List<ShopkeeperWarehouse> querylist();

    /**
     * 通过门店编号和商品编号查找对应库存记录
     *
     */
    ShopkeeperWarehouse queryForKey(String shopCode,String goodsCode);

    /**
     * 通过ID查询单条数据
     *
     * @param shopkeeperWarehouseKey 主键
     * @return 实例对象
     */
    ShopkeeperWarehouse queryById(String shopkeeperWarehouseKey);

    /**
     * 分页查询
     *
     * @param shopkeeperWarehouse 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(ShopkeeperWarehouse shopkeeperWarehouse, Integer startRows, Integer pageSize);

    /**
     * 新增数据
     *
     * @param shopkeeperWarehouse 实例对象
     * @return 实例对象
     */
    ShopkeeperWarehouse insert(ShopkeeperWarehouse shopkeeperWarehouse);

    /**
     * 修改数据
     *
     * @param shopkeeperWarehouse 实例对象
     * @return 实例对象
     */
    ShopkeeperWarehouse update(ShopkeeperWarehouse shopkeeperWarehouse);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(ShopkeeperWarehouse shopkeeperWarehouse);

}
