package com.example.api_project.service.impl;

import com.example.api_project.pojo.SupplierBilling;
import com.example.api_project.mapper.SupplierBillingMapper;
import com.example.api_project.service.SupplierBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商的开票信息对应表(SupplierBilling)表服务实现类
 *
 */
@Service("supplierBillingService")
public class SupplierBillingServiceImpl implements SupplierBillingService {
    @Autowired
    private SupplierBillingMapper supplierBillingMapper;

    /**
     * 不分页查询
     */
    public List<SupplierBilling> querylist() {
        return supplierBillingMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param billingKey 主键
     * @return 实例对象
     */
    @Override
    public SupplierBilling queryById(String billingKey) {
        return this.supplierBillingMapper.queryById(billingKey);
    }

    /**
     *
     * @param supplierBillingCode
     * @return 实例对象
     */
    @Override
    public List<SupplierBilling> queryBySupplierKey(String supplierBillingCode) {
        return supplierBillingMapper.queryBySupplierKey(supplierBillingCode);
    }

    /**
     * 分页查询
     *
     * @param supplierBilling 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(SupplierBilling supplierBilling, Integer startRows, Integer pageSize) {
        long total = this.supplierBillingMapper.count(supplierBilling);
        String accountName=supplierBilling.getAccountName();
        String bankName=supplierBilling.getBankName();
        List<SupplierBilling> records = this.supplierBillingMapper.queryAllByLimit(accountName,bankName,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param supplierBilling 实例对象
     * @return 实例对象
     */
    @Override
    public SupplierBilling insert(SupplierBilling supplierBilling) {
        this.supplierBillingMapper.insert(supplierBilling);
        return supplierBilling;
    }

    /**
     * 修改数据
     *
     * @param supplierBilling 实例对象
     * @return 实例对象
     */
    @Override
    public SupplierBilling update(SupplierBilling supplierBilling) {
        this.supplierBillingMapper.update(supplierBilling);
        return this.queryById(supplierBilling.getBillingKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param supplierBilling
     * @return 是否成功
     */
    @Override
    public boolean deleteById(SupplierBilling supplierBilling) {
        return this.supplierBillingMapper.deleteById(supplierBilling) > 0;
    }
}
