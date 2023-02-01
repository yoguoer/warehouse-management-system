package com.example.api_project.service.impl;

import com.example.api_project.pojo.Inventory;
import com.example.api_project.service.SupplierInventoryService;
import com.example.api_project.vo.SupplierInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.api_project.mapper.SupplierInventoryMapper;
import com.example.api_project.mapper.InventoryMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商仓库配置对应表(SupplierInventory)表服务实现类
 *
 */
@Service("SupplierInventoryService")
public class SupplierInventoryImpl implements SupplierInventoryService {
    @Autowired
    private SupplierInventoryMapper SupplierInventoryMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 不分页查询
     */
    public List<SupplierInventory> querylist() {
        return SupplierInventoryMapper.getList();
    }

    /**
     * 分页查询
     *
     * @param SupplierInventory 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(SupplierInventory SupplierInventory, Integer startRows, Integer pageSize) {
        String supplierKey=SupplierInventory.getSupplierKey();
        String supplierName=SupplierInventory.getSupplierName();
        String inventoryKey=SupplierInventory.getInventoryKey();
        String inventoryName=SupplierInventory.getInventoryName();
        Integer status=SupplierInventory.getStatus();
        List<SupplierInventory> records = this.SupplierInventoryMapper.queryAllByLimit(status,supplierKey,supplierName,inventoryKey,inventoryName,startRows, pageSize);
        long total = this.SupplierInventoryMapper.count(SupplierInventory);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 修改数据
     *
     * @param SupplierInventory 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SupplierInventory SupplierInventory) {
        return this.SupplierInventoryMapper.update(SupplierInventory);
    }

    /**
     * 通过主键删除数据
     *
     * @param SupplierInventory
     * @return 是否成功
     */
    @Override
    public boolean deleteById(SupplierInventory SupplierInventory) {
        String inventoryKey=SupplierInventory.getInventoryKey();
        Inventory inventory=new Inventory();
        inventory.setInventoryKey(inventoryKey);
        inventory.setBelongKey("");
        return this.inventoryMapper.update(inventory) > 0;
    }
}
