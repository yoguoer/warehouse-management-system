package com.example.api_project.service;

import com.example.api_project.pojo.Contact;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 联系人(Contact)表服务接口
 *
 */
public interface ContactService {

    /**
     * 不分页查询
     */
    List<Contact> querylist();

    /**
     * 根据customerKey
     */
    List<Contact> queryByCustomerKey(String contactCustomerKey);

    /**
     * 根据supplierKey
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
     * 分页查询
     *
     * @param contact 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Contact contact, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param contact 实例对象
     * @return 实例对象
     */
    Contact insert(Contact contact);

    /**
     * 修改数据
     *
     * @param contact 实例对象
     * @return 实例对象
     */
    Contact update(Contact contact);

    /**
     * 通过主键删除数据
     *
     * @param contact 主键
     * @return 是否成功
     */
    boolean deleteById(Contact contact);

}
