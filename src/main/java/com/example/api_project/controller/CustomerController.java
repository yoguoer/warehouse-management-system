package com.example.api_project.controller;

import com.example.api_project.mapper.ContactMapper;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Contact;
import com.example.api_project.pojo.Customer;
import com.example.api_project.service.ContactService;
import com.example.api_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 客户(Customer)表控制层
 *
 */
@RestController
@RequestMapping("api/customer")
public class CustomerController {
    /**
     * 服务对象
     */
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ContactService contactService;

    /**
     * 分页查询
     *
     * @return 不查询结果
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.customerService.querylist());
    }

    /**
     * 分页查询
     *
     * @param customer 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Customer customer, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.customerService.queryByPage(customer, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param customerKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("customerKey") String customerKey) {
        return ResponseData.success(this.customerService.queryById(customerKey));
    }

    /**
     * 新增数据
     *
     * @param customer 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Customer customer) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        customer.setCustomerKey(System.currentTimeMillis() + String.valueOf(number));
        List<Contact> customerContactList = customer.getCustomerContactList();
        String contactCustomerKey = customer.getCustomerKey();
        //插入联系人
        if(!customerContactList.isEmpty()){
            for (Contact contact : customerContactList) {
                contact.setContactCustomerKey(contactCustomerKey);
                Integer contactKey = random.nextInt(9000) + 1000;
                contact.setContactKey(System.currentTimeMillis() + String.valueOf(contactKey));
                this.contactService.insert(contact);
            }
        }
        //插入客户
        return ResponseData.success(this.customerService.insert(customer));
    }

    /**
     * 编辑数据
     *
     * @param customer 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Customer customer) {
        String contactCustomerKey = customer.getCustomerKey();//取出key
        List<Contact> customerContactList = customer.getCustomerContactList();//传入的数组
        Random random = new Random();
        Integer contactKey = random.nextInt(9000) + 1000;
        //修改传进来有值的联系人
        for (Contact contact : customerContactList) {
            String CONTACTKEY=contact.getContactKey();
            //添加(contactKey是空的)
            if(CONTACTKEY==null || CONTACTKEY==""){
                contact.setContactCustomerKey(contactCustomerKey);
                contact.setContactKey(System.currentTimeMillis() + String.valueOf(contactKey));
                this.contactService.insert(contact);
            }else{//contactKey非空,更新
                this.contactService.update(contact);
            }
        }
        //取出数据库中现有的
        List<Contact> CurrentList = this.contactService.queryByCustomerKey(contactCustomerKey);
        List<String> newList = new ArrayList<>();
        //把存进来的列表中数据的key留起来
        customerContactList.forEach(item -> {
            if (!newList.contains(item.getContactKey())) {
                newList.add(item.getContactKey());
            }
        });
        //取出缺少的，那些就是要删掉的
        CurrentList.forEach(item -> {
            if (!newList.contains(item.getContactKey())) {
                this.contactService.deleteById(item);
            }
        });
        //修改用户本身
        return ResponseData.success(this.customerService.update(customer));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Customer customer) {
        if(this.customerService.deleteById(customer)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.customerService.deleteById(customer));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Customer> customerKeys) {
        for(Customer customer: customerKeys){
            this.customerService.deleteById(customer);
        }
        return ResponseData.success();
    }
}

