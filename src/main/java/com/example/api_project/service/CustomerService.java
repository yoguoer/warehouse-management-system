package com.example.api_project.service;


import com.example.api_project.pojo.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户(Customer)表服务接口
 *
 */
public interface CustomerService {

    /**
     * 不分页查询
     */
    List<Customer> querylist();


    /**
     * 通过ID查询单条数据
     *
     * @param customerKey 主键
     * @return 实例对象
     */
    Customer queryById(String customerKey);

    /**
     * 分页查询
     *
     * @param customer 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Customer customer, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param customer 实例对象
     * @return 实例对象
     */
    Customer insert(Customer customer);

    /**
     * 修改数据
     *
     * @param customer 实例对象
     * @return 实例对象
     */
    Customer update(Customer customer);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(Customer customer);

}
