package com.example.api_project.service;

import com.example.api_project.pojo.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商(Supplier)表服务接口
 *
 */
public interface SupplierService {

    boolean setNoCategory(String categoryKey);

    /**
     * 通过ID查询单条数据
     *
     * @param supplierKey 主键
     * @return 实例对象
     */
    Supplier queryById(String supplierKey);

    /**
     * 不分页查询
     */
    List<Supplier> querylist();

    /**
     * 分页查询
     *
     * @param supplier    筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Supplier supplier, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param supplier 实例对象
     * @return 实例对象
     */
    Supplier insert(Supplier supplier);

    /**
     * 修改数据
     *
     * @param supplier 实例对象
     * @return 实例对象
     */
    Supplier update(Supplier supplier);

    /**
     * 通过主键删除数据
     *
     * @param supplier
     * @return 是否成功
     */
    boolean deleteById(Supplier supplier);

}
