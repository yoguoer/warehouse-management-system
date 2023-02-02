package com.example.api_project.service.impl;

import com.example.api_project.mapper.ContactMapper;
import com.example.api_project.pojo.Contact;
import com.example.api_project.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人(Contact)表服务实现类
 *
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactMapper contactMapper;

    /**
     * 不分页查询
     */
    public List<Contact> querylist() {
        return contactMapper.getList();
    }

    /**
     * 根据customerKey
     */
    public List<Contact> queryByCustomerKey(String contactCustomerKey) {
        return contactMapper.queryByCustomerKey(contactCustomerKey);
    }

    /**
     * 根据supplierKey
     */
    public List<Contact> queryBySupplierKey(String contactSupplierKey) {
        return contactMapper.queryBySupplierKey(contactSupplierKey);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param contactKey 主键
     * @return 实例对象
     */
    @Override
    public Contact queryById(String contactKey) {
        return this.contactMapper.queryById(contactKey);
    }

    /**
     * 分页查询
     *
     * @param contact 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Contact contact, Integer startRows, Integer pageSize){
        String contactName=contact.getContactName();
        String contactCustomerKey=contact.getContactCustomerKey();
        List<Contact> records = this.contactMapper.queryAllByCustomer(contactName,contactCustomerKey, startRows, pageSize);
        long total = this.contactMapper.countByCustomer(contact);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param contact 实例对象
     * @return 实例对象
     */
    @Override
    public Contact insert(Contact contact) {
        this.contactMapper.insert(contact);
        return contact;
    }

    /**
     * 修改数据
     *
     * @param contact 实例对象
     * @return 实例对象
     */
    @Override
    public Contact update(Contact contact) {
        this.contactMapper.update(contact);
        return this.queryById(contact.getContactName());
    }

    /**
     * 通过主键删除数据
     *
     * @param contact 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Contact contact) {
        return this.contactMapper.deleteById(contact) > 0;
    }
}
