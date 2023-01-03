package com.example.api_project.mapper;

import com.example.api_project.pojo.SupplierAddress;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 供应商的地址表(SupplierAddress)表数据库访问层
 *
 */
public interface SupplierAddressMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<SupplierAddress> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param addressKey 主键
     * @return 实例对象
     */
    SupplierAddress queryById(String addressKey);

    /**
     * 查询指定行数据
     *
     * @param supplierAddress 查询条件
     * @return 对象列表
     */
    List<SupplierAddress> queryAllByLimit(SupplierAddress supplierAddress,Integer startRows, Integer pageSize);

    /**
     * 查询指定行数据(供应商)
     *
     * @return queryBySupplierKey
     */
    List<SupplierAddress> queryBySupplierKey(String supplierAddressKey);

    /**
     * 统计总行数
     *
     * @param supplierAddress 查询条件
     * @return 总行数
     */
    long count(SupplierAddress supplierAddress);

    /**
     * 新增数据
     *
     * @param supplierAddress 实例对象
     * @return 影响行数
     */
    int insert(SupplierAddress supplierAddress);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SupplierAddress> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SupplierAddress> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SupplierAddress> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SupplierAddress> entities);

    /**
     * 修改数据
     *
     * @param supplierAddress 实例对象
     * @return 影响行数
     */
    int update(SupplierAddress supplierAddress);

    /**
     * 通过主键删除数据
     *
     * @param supplierAddress
     * @return 影响行数
     */
    int deleteById(SupplierAddress supplierAddress);

}

