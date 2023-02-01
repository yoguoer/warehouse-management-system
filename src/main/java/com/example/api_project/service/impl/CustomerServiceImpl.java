package com.example.api_project.service.impl;

import com.example.api_project.mapper.ContactMapper;
import com.example.api_project.mapper.CustomerMapper;
import com.example.api_project.pojo.Contact;
import com.example.api_project.pojo.Customer;
import com.example.api_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户(Customer)表服务实现类
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactMapper contactMapper;


    /**
     * 不分页查询
     */
    public List<Customer> querylist() {
        return customerMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param customerKey 主键
     * @return 实例对象
     */
    @Override
    public Customer queryById(String customerKey) {
        return this.customerMapper.queryById(customerKey);
    }

    /**
     * 分页查询
     *
     * @param customer 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Customer customer, Integer startRows, Integer pageSize){
        String customerName=customer.getCustomerName();
        List<Customer> records;
        if(null!=customer.getCategoryKey() && customer.getCategoryKey().equals("all_CUSTOMER")){
            String categoryKey="";
            customer.setCategoryKey("");
            records = this.customerMapper.queryAllByLimit(categoryKey,customerName,startRows, pageSize);
        }else{
            String categoryKey=customer.getCategoryKey();
            records = this.customerMapper.queryAllByLimit(categoryKey,customerName,startRows, pageSize);
        }
//        String categoryKey=customer.getCategoryKey();
//        List<Customer> records = customerMapper.queryAllByLimit(categoryKey,customerName,startRows, pageSize);
        for (Customer item : records) {
            String contactCustomerKey= item.getCustomerKey();
            List<Contact> customerContactList=contactMapper.queryByCustomerKey(contactCustomerKey);
            if(null!=customerContactList){
                item.setCustomerContactList(customerContactList);
            }
        }
        long total = this.customerMapper.count(customer);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param customer 实例对象
     * @return 实例对象
     */
    @Override
    public Customer insert(Customer customer) {
        this.customerMapper.insert(customer);
        return customer;
    }

    /**
     * 修改数据
     *
     * @param customer 实例对象
     * @return 实例对象
     */
    @Override
    public Customer update(Customer customer) {
        this.customerMapper.update(customer);
        return this.queryById(customer.getCustomerKey());
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Customer customer) {
        return this.customerMapper.deleteById(customer) > 0;
    }
}
