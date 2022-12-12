package com.example.api_project.mapper;

import com.example.api_project.pojo.Customer;
import com.example.api_project.pojo.District;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户(Customer)表数据库访问层
 *
 */
public interface CustomerMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Customer> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param customerKey 主键
     * @return 实例对象
     */
    Customer queryById(String customerKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Customer> queryAllByLimit(String categoryKey, String customerName,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param customer 查询条件
     * @return 总行数
     */
    long count(Customer customer);

    /**
     * 新增数据
     *
     * @param customer 实例对象
     * @return 影响行数
     */
    int insert(Customer customer);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Customer> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Customer> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Customer> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Customer> entities);

    /**
     * 修改数据
     *
     * @param customer 实例对象
     * @return 影响行数
     */
    int update(Customer customer);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(Customer customer);

}

