package com.example.api_project.service;

import com.example.api_project.pojo.Inventory;
import com.example.api_project.pojo.SupplierAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商的地址表(SupplierAddress)表服务接口
 *
 */
public interface SupplierAddressService {

    /**
     * 不分页查询
     */
    List<SupplierAddress> querylist();

    List<SupplierAddress> queryBySupplierKey(String supplierAddressKey);

    /**
     * 通过ID查询单条数据
     *
     * @param addressKey 主键
     * @return 实例对象
     */
    SupplierAddress queryById(String addressKey);

    /**
     * 分页查询
     *
     * @param supplierAddress 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(SupplierAddress supplierAddress, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param supplierAddress 实例对象
     * @return 实例对象
     */
    SupplierAddress insert(SupplierAddress supplierAddress);

    /**
     * 修改数据
     *
     * @param supplierAddress 实例对象
     * @return 实例对象
     */
    SupplierAddress update(SupplierAddress supplierAddress);

    /**
     * 通过主键删除数据
     *
     * @param supplierAddress
     * @return 是否成功
     */
    boolean deleteById(SupplierAddress supplierAddress);

}
