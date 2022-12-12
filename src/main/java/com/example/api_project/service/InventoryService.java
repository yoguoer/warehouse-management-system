package com.example.api_project.service;

import com.example.api_project.pojo.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 仓库(Inventory)表服务接口
 *
 */
public interface InventoryService {

    /**
     * 不分页查询
     */
    List<Inventory> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @param inventoryKey 主键
     * @return 实例对象
     */
    Inventory queryById(String inventoryKey);

    /**
     * 分页查询
     *
     * @param inventory 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Inventory inventory, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    Inventory insert(Inventory inventory);

    /**
     * 修改数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    Inventory update(Inventory inventory);

    /**
     * 通过主键删除数据
     *
     * @param inventory
     * @return 是否成功
     */
    boolean deleteById(Inventory inventory);

}
