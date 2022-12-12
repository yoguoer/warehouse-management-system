package com.example.api_project.service.impl;

import com.example.api_project.mapper.SupplierAddressMapper;
import com.example.api_project.pojo.Inventory;
import com.example.api_project.pojo.SupplierAddress;
import com.example.api_project.service.SupplierAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商的地址表(SupplierAddress)表服务实现类
 *
 */
@Service("supplierAddressService")
public class SupplierAddressServiceImpl implements SupplierAddressService {
    @Autowired
    private SupplierAddressMapper supplierAddressMapper;

    /**
     * 不分页查询
     */
    public List<SupplierAddress> querylist() {
        return supplierAddressMapper.getList();
    }

    /**
     *
     * @param supplierAddressKey
     * @return 实例对象
     */
    @Override
    public List<SupplierAddress> queryBySupplierKey(String supplierAddressKey) {
        return supplierAddressMapper.queryBySupplierKey(supplierAddressKey);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param addressKey 主键
     * @return 实例对象
     */
    @Override
    public SupplierAddress queryById(String addressKey) {
        return this.supplierAddressMapper.queryById(addressKey);
    }

    /**
     * 分页查询
     *
     * @param supplierAddress 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(SupplierAddress supplierAddress, Integer startRows, Integer pageSize) {
        long total = this.supplierAddressMapper.count(supplierAddress);
        List<SupplierAddress> records = this.supplierAddressMapper.queryAllByLimit(supplierAddress,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param supplierAddress 实例对象
     * @return 实例对象
     */
    @Override
    public SupplierAddress insert(SupplierAddress supplierAddress) {
        this.supplierAddressMapper.insert(supplierAddress);
        return supplierAddress;
    }

    /**
     * 修改数据
     *
     * @param supplierAddress 实例对象
     * @return 实例对象
     */
    @Override
    public SupplierAddress update(SupplierAddress supplierAddress) {
        this.supplierAddressMapper.update(supplierAddress);
        return this.queryById(supplierAddress.getAddressKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param supplierAddress
     * @return 是否成功
     */
    @Override
    public boolean deleteById(SupplierAddress supplierAddress) {
        return this.supplierAddressMapper.deleteById(supplierAddress) > 0;
    }
}
