package com.example.api_project.service;

import com.example.api_project.vo.ShopInventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门店仓库配置对应表(ShopInventory)表服务接口
 *
 */
public interface ShopInventoryService {

    /**
     * 不分页查询
     */
    List<ShopInventory> querylist(ShopInventory ShopInventory);

    /**
     * 分页查询
     *
     * @param ShopInventory 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(ShopInventory ShopInventory, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 通过(shopCode)查询单条数据
     *
     * @return 实例对象
     */
    List<ShopInventory> queryByshopCode(ShopInventory ShopInventory);

    /**
     * 修改数据
     *
     * @param ShopInventory 实例对象
     * @return 实例对象
     */
    Boolean update(ShopInventory ShopInventory);

    /**
     * 通过主键删除数据
     *
     * @param ShopInventory
     * @return 是否成功
     */
    boolean deleteById(ShopInventory ShopInventory);

}
