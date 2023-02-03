package com.example.api_project.service.impl;

import com.example.api_project.mapper.ContactMapper;
import com.example.api_project.mapper.SupplierAddressMapper;
import com.example.api_project.mapper.SupplierBillingMapper;
import com.example.api_project.mapper.SupplierMapper;
import com.example.api_project.pojo.*;
import com.example.api_project.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商(Supplier)表服务实现类
 *
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private SupplierAddressMapper supplierAddressMapper;

    @Autowired
    private SupplierBillingMapper supplierBillingMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param supplierKey 主键
     * @return 实例对象
     */
    @Override
    public Supplier queryById(String supplierKey) {
        return this.supplierMapper.queryById(supplierKey);
    }

    /**
     * 不分页查询
     */
    public List<Supplier> querylist() {
        List<Supplier> records=supplierMapper.getList();
//        String categoryKey=supplier.getCategoryKey();
//        List<Supplier> records = this.supplierMapper.queryAllByLimit(supplierName,supplierCode,categoryKey,startRows, pageSize);
        for (Supplier item : records) {
            //获取联系人列表
            String contactSupplierKey= item.getSupplierKey();
            List<Contact> supplierContactList=this.contactMapper.queryBySupplierKey(contactSupplierKey);
            if(null!=supplierContactList) {
                item.setSupplierContactList(supplierContactList);
            }
            //获取地址列表
            String supplierAddressKey= item.getSupplierKey();
            List<SupplierAddress> supplierAddressList=this.supplierAddressMapper.queryBySupplierKey(supplierAddressKey);
            if(null!=supplierAddressList) {
                item.setSupplierAddressList(supplierAddressList);
            }
            //获取开票信息列表
            String supplierBillingKey= item.getSupplierKey();
            List<SupplierBilling> supplierBillingList=this.supplierBillingMapper.queryBySupplierKey(supplierBillingKey);
            if(null!=supplierBillingList) {
                item.setSupplierBillingList(supplierBillingList);
            }
        }
        return records;
    }

    /**
     * 分页查询
     *
     * @param supplier    筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Supplier supplier, Integer startRows, Integer pageSize) {
        String supplierName=supplier.getSupplierName();
        String supplierCode = supplier.getSupplierCode();
        List<Supplier> records;
        if(null!=supplier.getCategoryKey() && supplier.getCategoryKey().equals("all_SUPPLIER")){
            String categoryKey="";
            supplier.setCategoryKey("");
            records = this.supplierMapper.queryAllByLimit(supplierName,supplierCode,categoryKey,startRows, pageSize);
        }else{
            String categoryKey=supplier.getCategoryKey();
            records = this.supplierMapper.queryAllByLimit(supplierName,supplierCode,categoryKey,startRows, pageSize);
        }
        if(records!=null){
            for (Supplier item : records) {
                //获取联系人列表
                String contactSupplierKey= item.getSupplierKey();
                List<Contact> supplierContactList=this.contactMapper.queryBySupplierKey(contactSupplierKey);
                if(null!=supplierContactList) {
                    item.setSupplierContactList(supplierContactList);
                }
                //获取地址列表
                String supplierAddressKey= item.getSupplierKey();
                List<SupplierAddress> supplierAddressList=this.supplierAddressMapper.queryBySupplierKey(supplierAddressKey);
                if(null!=supplierAddressList) {
                    item.setSupplierAddressList(supplierAddressList);
                }
                //获取开票信息列表
                String supplierBillingKey= item.getSupplierKey();
                List<SupplierBilling> supplierBillingList=this.supplierBillingMapper.queryBySupplierKey(supplierBillingKey);
                if(null!=supplierBillingList) {
                    item.setSupplierBillingList(supplierBillingList);
                }
            }
        }
        long total = this.supplierMapper.count(supplier);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param supplier 实例对象
     * @return 实例对象
     */
    @Override
    public Supplier insert(Supplier supplier) {
        this.supplierMapper.insert(supplier);
        return supplier;
    }

    /**
     * 修改数据
     *
     * @param supplier 实例对象
     * @return 实例对象
     */
    @Override
    public Supplier update(Supplier supplier) {
        this.supplierMapper.update(supplier);
        return this.queryById(supplier.getSupplierKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param supplier
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Supplier supplier) {
        return this.supplierMapper.deleteById(supplier) > 0;
    }
}
