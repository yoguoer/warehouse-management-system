package com.example.api_project.controller;


import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Contact;
import com.example.api_project.pojo.Supplier;
import com.example.api_project.pojo.SupplierAddress;
import com.example.api_project.pojo.SupplierBilling;
import com.example.api_project.service.ContactService;
import com.example.api_project.service.SupplierAddressService;
import com.example.api_project.service.SupplierBillingService;
import com.example.api_project.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 供应商(Supplier)表控制层
 *
 */
@RestController
@RequestMapping("api/supplier")
@CrossOrigin(origins = "*")

public class SupplierController {
    /**
     * 服务对象
     */
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private SupplierAddressService supplierAddressService;

    @Autowired
    private SupplierBillingService supplierBillingService;

    /**
     * 不分页查询
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result queryList() {
        return ResponseData.success(this.supplierService.querylist());
    }

    /**
     * 分页查询
     *
     * @param supplier    筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Supplier supplier, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.supplierService.queryByPage(supplier, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(Supplier supplier) {
        String supplierKey=supplier.getSupplierKey();
        return ResponseData.success(this.supplierService.queryById(supplierKey));
    }

    /**
     * 新增数据
     *
     * @param supplier 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Supplier supplier) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        supplier.setSupplierKey(System.currentTimeMillis() + String.valueOf(number));

        //插入联系人
        List<Contact> supplierContactList = supplier.getSupplierContactList();
        String contactSupplierKey = supplier.getSupplierKey();
        for (Contact contact : supplierContactList) {
            contact.setContactSupplierKey(contactSupplierKey);
            Integer contactKey = random.nextInt(9000) + 1000;
            contact.setContactKey(System.currentTimeMillis() + String.valueOf(contactKey));
            this.contactService.insert(contact);
        }
        //插入联系地址
        List<SupplierAddress> supplierAddressList = supplier.getSupplierAddressList();
        String supplierAddressKey = supplier.getSupplierKey();
        for (SupplierAddress supplierAddress : supplierAddressList) {
            supplierAddress.setSupplierAddressKey(supplierAddressKey);
            Integer addressKey = random.nextInt(9000) + 1000;
            supplierAddress.setAddressKey(System.currentTimeMillis() + String.valueOf(addressKey));
            this.supplierAddressService.insert(supplierAddress);
        }
        //插入开票信息
        List<SupplierBilling> supplierBillingList = supplier.getSupplierBillingList();
        String supplierBillingKey = supplier.getSupplierKey();
        for (SupplierBilling supplierBilling : supplierBillingList) {
            supplierBilling.setSupplierBillingKey(supplierBillingKey);
            Integer billingKey = random.nextInt(9000) + 1000;
            supplierBilling.setBillingKey(System.currentTimeMillis() + String.valueOf(billingKey));
            this.supplierBillingService.insert(supplierBilling);
        }
        //插入自己
        return ResponseData.success(this.supplierService.insert(supplier));
    }

    /**
     * 编辑数据
     *
     * @param supplier 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Supplier supplier) {
        //修改联系人
        String contactSupplierKey = supplier.getSupplierKey();//取出key
        List<Contact> supplierContactList = supplier.getSupplierContactList();
        Random random = new Random();
        Integer contactKey = random.nextInt(9000) + 1000;
        for (Contact contact : supplierContactList) {
            String CONTACTKEY=contact.getContactKey();
            //添加(contactKey是空的)
            if(CONTACTKEY==null || CONTACTKEY==""){
                contact.setContactSupplierKey(contactSupplierKey);
                contact.setContactKey(System.currentTimeMillis() + String.valueOf(contactKey));
                this.contactService.insert(contact);
            }else{//contactKey非空,更新
                this.contactService.update(contact);
            }
        }
        //取出数据库中现有的
        List<Contact> contactList = this.contactService.queryBySupplierKey(contactSupplierKey);
        List<String> newList = new ArrayList<>();
        //把存进来的列表中数据的key留起来
        supplierContactList.forEach(item -> {
            if (!newList.contains(item.getContactKey())) {
                newList.add(item.getContactKey());
            }
        });
        //取出缺少的，那些就是要删掉的
        contactList.forEach(item -> {
            if (!newList.contains(item.getContactKey())) {
                this.contactService.deleteById(item);
            }
        });

        //修改联系地址
        String supplierAddressKey = supplier.getSupplierKey();//取出key
        List<SupplierAddress> supplierAddressList = supplier.getSupplierAddressList();
        for (SupplierAddress supplierAddress : supplierAddressList) {
            String ADDRESSKEY=supplierAddress.getAddressKey();
            //添加(ADDRESSKEY)
            if(ADDRESSKEY==null || ADDRESSKEY==""){
                supplierAddress.setSupplierAddressKey(supplierAddressKey);
                supplierAddress.setAddressKey(System.currentTimeMillis() + String.valueOf(contactKey));
                this.supplierAddressService.insert(supplierAddress);
            }else{//ADDRESSKEY非空,更新
                this.supplierAddressService.update(supplierAddress);
            }
        }
        //取出数据库中现有的
        List<SupplierAddress> addressList = this.supplierAddressService.queryBySupplierKey(supplierAddressKey);
        List<String> newList1 = new ArrayList<>();
        //把存进来的列表中数据的key留起来
        supplierAddressList.forEach(item -> {
            if (!newList1.contains(item.getAddressKey())) {
                newList1.add(item.getAddressKey());
            }
        });
        //取出缺少的，那些就是要删掉的
        addressList.forEach(item -> {
            if (!newList1.contains(item.getAddressKey())) {
                this.supplierAddressService.deleteById(item);
            }
        });

        //修改开票信息
        String supplierBillingKey = supplier.getSupplierKey();//取出code
        List<SupplierBilling> supplierBillingList = supplier.getSupplierBillingList();
        for (SupplierBilling supplierBilling : supplierBillingList) {
            String BELLINGKEY=supplierBilling.getBillingKey();
            //添加(BELLINGKEY)
            if(BELLINGKEY==null || BELLINGKEY==""){
                supplierBilling.setSupplierBillingKey(supplierBillingKey);
                supplierBilling.setBillingKey(System.currentTimeMillis() + String.valueOf(contactKey));
                this.supplierBillingService.insert(supplierBilling);
            }else{//BELLINGKEY,更新
                this.supplierBillingService.update(supplierBilling);
            }
        }
        //取出数据库中现有的
        List<SupplierBilling> billingList = this.supplierBillingService.queryBySupplierKey(supplierBillingKey);
        List<String> newList2 = new ArrayList<>();
        //把存进来的列表中数据的key留起来
        supplierBillingList.forEach(item -> {
            if (!newList2.contains(item.getSupplierBillingKey())) {
                newList2.add(item.getSupplierBillingKey());
            }
        });
        //取出缺少的，那些就是要删掉的
        billingList.forEach(item -> {
            if (!newList2.contains(item.getBillingKey())) {
                this.supplierBillingService.deleteById(item);
            }
        });

        //修改供应商本身
        return ResponseData.success(this.supplierService.update(supplier));
    }

    /**
     * 删除数据
     *
     * @param supplier
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Supplier supplier) {
        if(this.supplierService.deleteById(supplier)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.supplierService.deleteById(supplier));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Supplier> supplierKeys) {
        for(Supplier supplier: supplierKeys){
            this.supplierService.deleteById(supplier);
        }
        return ResponseData.success();
    }
}

