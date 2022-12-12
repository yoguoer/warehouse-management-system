package com.example.api_project.mapper;

import com.example.api_project.pojo.District;
import com.example.api_project.pojo.Goods;
import com.example.api_project.pojo.Inventory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 仓库(Inventory)表数据库访问层
 *
 */
public interface InventoryMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Inventory> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param inventoryKey 主键
     * @return 实例对象
     */
    Inventory queryById(String inventoryKey);


    /**
     * 查询指全部数据
     *
     * @param inventory 查询条件
     * @return 对象列表
     */
    List<Inventory> getListPage(Inventory inventory, Integer startRows, Integer pageSize);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Inventory> queryAllByLimit(String inventoryName, String categoryKey, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param inventory 查询条件
     * @return 总行数
     */
    long count(Inventory inventory);

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 影响行数
     */
    int insert(Inventory inventory);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Inventory> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Inventory> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Inventory> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Inventory> entities);

    /**
     * 修改数据
     *
     * @param inventory 实例对象
     * @return 影响行数
     */
    int update(Inventory inventory);

    /**
     * 通过主键删除数据
     *
     * @param inventory
     * @return 影响行数
     */
    int deleteById(Inventory inventory);

}

