package com.example.api_project.mapper;

import com.example.api_project.pojo.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 供应商(Supplier)表数据库访问层
 *
 */
public interface SupplierMapper {

    boolean setNoCategory(Supplier supplier);

    //    根据id查供应商名称
    String queryNameById(String supplierKey);

    /**
     * 通过ID查询单条数据
     *
     * @param supplierKey 主键
     * @return 实例对象
     */
    Supplier queryById(String supplierKey);

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Supplier> getList();

    List<Supplier> getListCategory(String categoryKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Supplier> queryAllByLimit(String supplierName,String supplierCode,String categoryKey, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param supplier 查询条件
     * @return 总行数
     */
    long count(Supplier supplier);

    /**
     * 新增数据
     *
     * @param supplier 实例对象
     * @return 影响行数
     */
    int insert(Supplier supplier);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Supplier> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Supplier> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Supplier> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Supplier> entities);

    /**
     * 修改数据
     *
     * @param supplier 实例对象
     * @return 影响行数
     */
    int update(Supplier supplier);

    /**
     * 通过主键删除数据
     *
     * @param supplier
     * @return 影响行数
     */
    int deleteById(Supplier supplier);

}

