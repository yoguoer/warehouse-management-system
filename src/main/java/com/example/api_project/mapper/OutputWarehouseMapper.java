package com.example.api_project.mapper;

import com.example.api_project.pojo.OutputWarehouse;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 出库表(OutputWarehouse)表数据库访问层
 *
 */
public interface OutputWarehouseMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<OutputWarehouse> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param outputWarehouseKey 主键
     * @return 实例对象
     */
    OutputWarehouse queryById(String outputWarehouseKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<OutputWarehouse> queryAllByLimit(String shopCode,String goodsCode,String customerCode,String inventoryCode,Integer status,Integer type,Integer isDeleted, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param outputWarehouse 查询条件
     * @return 总行数
     */
    long count(OutputWarehouse outputWarehouse);

    /**
     * 新增数据
     *
     * @param outputWarehouse 实例对象
     * @return 影响行数
     */
    int insert(OutputWarehouse outputWarehouse);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutputWarehouse> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<OutputWarehouse> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<OutputWarehouse> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<OutputWarehouse> entities);

    /**
     * 修改数据
     *
     * @param outputWarehouse 实例对象
     * @return 影响行数
     */
    int update(OutputWarehouse outputWarehouse);

    /**
     * 通过主键删除数据
     *
     * @param outputWarehouse
     * @return 影响行数
     */
    int deleteById(OutputWarehouse outputWarehouse);

}

