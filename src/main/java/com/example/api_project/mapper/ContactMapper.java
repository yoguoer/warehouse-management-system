package com.example.api_project.mapper;

import com.example.api_project.pojo.Contact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 联系人(Contact)表数据库访问层
 *
 */
public interface ContactMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Contact> getList();

    /**
     * 通过客户key查询单条数据
     *
     * @param
     * @return 实例对象
     */
    List<Contact> queryByCustomerKey(String contactCustomerKey);

    /**
     * 查询指定行数据(供应商)
     *
     * @return 对象列表
     */
    List<Contact> queryBySupplierKey(String contactSupplierKey);

    /**
     * 通过ID查询单条数据
     *
     * @param contactKey 主键
     * @return 实例对象
     */
    Contact queryById(String contactKey);

    /**
     * 查询指定行数据(客户)
     *
     * @return 对象列表
     */
    List<Contact> queryAllByCustomer(String contactName,String contactCustomerKey, Integer startRows, Integer pageSize);


    /**
     * 统计总行数
     *
     * @param contact 查询条件
     * @return 总行数
     */
    long count(Contact contact);

    long countByCustomer(Contact contact);

    /**
     * 新增数据
     *
     * @param contact 实例对象
     * @return 影响行数
     */
    int insert(Contact contact);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Contact> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Contact> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Contact> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Contact> entities);

    /**
     * 修改数据
     *
     * @param contact 实例对象
     * @return 影响行数
     */
    int update(Contact contact);

    /**
     * 通过主键删除数据
     *
     * @param contact 主键
     * @return 影响行数
     */
    int deleteById(Contact contact);

}

