package com.example.api_project.mapper;

import com.example.api_project.pojo.SupplierBilling;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商的开票信息对应表(SupplierBilling)表数据库访问层
 *
 */
public interface SupplierBillingMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<SupplierBilling> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param billingKey 主键
     * @return 实例对象
     */
    SupplierBilling queryById(String billingKey);

    /**
     * 查询指定行数据(供应商)
     *
     * @return queryBySupplierKey
     */
    List<SupplierBilling> queryBySupplierKey(String supplierBillingCode);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<SupplierBilling> queryAllByLimit( String accountName,String bankName,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param supplierBilling 查询条件
     * @return 总行数
     */
    long count(SupplierBilling supplierBilling);

    /**
     * 新增数据
     *
     * @param supplierBilling 实例对象
     * @return 影响行数
     */
    int insert(SupplierBilling supplierBilling);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SupplierBilling> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SupplierBilling> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SupplierBilling> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<SupplierBilling> entities);

    /**
     * 修改数据
     *
     * @param supplierBilling 实例对象
     * @return 影响行数
     */
    int update(SupplierBilling supplierBilling);

    /**
     * 通过主键删除数据
     *
     * @param supplierBilling
     * @return 影响行数
     */
    int deleteById(SupplierBilling supplierBilling);

}

