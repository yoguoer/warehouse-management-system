package com.example.api_project.mapper;

import com.example.api_project.pojo.ShopkeeperWarehouse;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 零售商商品库存表(ShopkeeperWarehouse)表数据库访问层
 *
 */
public interface ShopkeeperWarehouseMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<ShopkeeperWarehouse> getList();

    /**
     * 查询指全部数据
     * 通过shopCode查询单条数据
     * @return 对象列表
     */
    List<ShopkeeperWarehouse> getByShopCode(String shopCode,String shopName);

    /**
     * 通过门店编号和商品编号查找对应库存记录
     *
     * @return 实例对象
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
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<ShopkeeperWarehouse> queryAllByLimit(String goodsCode,String shopCode, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param shopkeeperWarehouse 查询条件
     * @return 总行数
     */
    long count(ShopkeeperWarehouse shopkeeperWarehouse);

    /**
     * 新增数据
     *
     * @param shopkeeperWarehouse 实例对象
     * @return 影响行数
     */
    int insert(ShopkeeperWarehouse shopkeeperWarehouse);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ShopkeeperWarehouse> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ShopkeeperWarehouse> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ShopkeeperWarehouse> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ShopkeeperWarehouse> entities);

    /**
     * 修改数据
     *
     * @param shopkeeperWarehouse 实例对象
     * @return 影响行数
     */
    int update(ShopkeeperWarehouse shopkeeperWarehouse);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(ShopkeeperWarehouse shopkeeperWarehouse);

}

