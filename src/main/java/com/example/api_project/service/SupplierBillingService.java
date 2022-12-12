package com.example.api_project.service;

import com.example.api_project.pojo.SupplierAddress;
import com.example.api_project.pojo.SupplierBilling;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商的开票信息对应表(SupplierBilling)表服务接口
 *
 */
public interface SupplierBillingService {

    /**
     * 不分页查询
     */
    List<SupplierBilling> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @param billingKey 主键
     * @return 实例对象
     */
    SupplierBilling queryById(String billingKey);

    List<SupplierBilling> queryBySupplierKey(String supplierBillingKey);

    /**
     * 分页查询
     *
     * @param supplierBilling 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(SupplierBilling supplierBilling, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param supplierBilling 实例对象
     * @return 实例对象
     */
    SupplierBilling insert(SupplierBilling supplierBilling);

    /**
     * 修改数据
     *
     * @param supplierBilling 实例对象
     * @return 实例对象
     */
    SupplierBilling update(SupplierBilling supplierBilling);

    /**
     * 通过主键删除数据
     *
     * @param supplierBilling
     * @return 是否成功
     */
    boolean deleteById(SupplierBilling supplierBilling);

}
