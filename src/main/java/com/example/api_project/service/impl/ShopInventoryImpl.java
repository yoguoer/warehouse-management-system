package com.example.api_project.service.impl;

import com.example.api_project.mapper.InventoryMapper;
import com.example.api_project.mapper.ShopInventoryMapper;
import com.example.api_project.pojo.Inventory;
import com.example.api_project.service.ShopInventoryService;
import com.example.api_project.vo.ShopInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 供应商的开票信息对应表(ShopInventory)表服务实现类
 *
 */
@Service("ShopInventoryService")
public class ShopInventoryImpl implements ShopInventoryService {
    @Autowired
    private ShopInventoryMapper ShopInventoryMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 不分页查询
     */
    public List<ShopInventory> querylist() {
        return ShopInventoryMapper.getList();
    }
    /**
     * 分页查询
     *
     * @param ShopInventory 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(ShopInventory ShopInventory, Integer startRows, Integer pageSize) {
        long total = this.ShopInventoryMapper.count(ShopInventory);
        String shopKey=ShopInventory.getShopKey();
        String shopName=ShopInventory.getShopName();
        String inventoryKey=ShopInventory.getInventoryKey();
        String inventoryName=ShopInventory.getInventoryName();
        Integer status=ShopInventory.getStatus();
        List<ShopInventory> records = this.ShopInventoryMapper.queryAllByLimit(status,shopKey,shopName,inventoryKey,inventoryName,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 修改数据
     *
     * @param ShopInventory 实例对象
     * @return 实例对象
     */
    @Override
    public int update(ShopInventory ShopInventory) {
        return this.ShopInventoryMapper.update(ShopInventory);
    }

    /**
     * 通过主键删除数据
     *
     * @param ShopInventory
     * @return 是否成功
     */
    @Override
    public boolean deleteById(ShopInventory ShopInventory) {
        String inventoryKey=ShopInventory.getInventoryKey();
        Inventory inventory=new Inventory();
        inventory.setInventoryKey(inventoryKey);
        inventory.setBelongKey("");
        return this.inventoryMapper.update(inventory) > 0;
    }

    /**
     * 通过(shopCode)查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public ShopInventory queryByshopCode(ShopInventory ShopInventory) {
        String shopCode=ShopInventory.getShopCode();
        return this.ShopInventoryMapper.getByshopCode(shopCode);
    }
}
