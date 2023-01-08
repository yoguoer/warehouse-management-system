package com.example.api_project.mapper;

import com.example.api_project.pojo.InputWarehouse;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 入库表(InputWarehouse)表数据库访问层
 *
 */
public interface InputWarehouseMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<InputWarehouse> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param inputWarehouseKey 主键
     * @return 实例对象
     */
    InputWarehouse queryById(String inputWarehouseKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<InputWarehouse> queryAllByLimit(String shopCode,String goodsCode,String supplierCode,String inventoryCode,Integer status,Integer type,Integer isDeleted, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param inputWarehouse 查询条件
     * @return 总行数
     */
    long count(InputWarehouse inputWarehouse);

    /**
     * 新增数据
     *
     * @param inputWarehouse 实例对象
     * @return 影响行数
     */
    int insert(InputWarehouse inputWarehouse);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<InputWarehouse> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<InputWarehouse> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<InputWarehouse> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<InputWarehouse> entities);

    /**
     * 修改数据
     *
     * @param inputWarehouse 实例对象
     * @return 影响行数
     */
    int update(InputWarehouse inputWarehouse);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(InputWarehouse inputWarehouse);

}

