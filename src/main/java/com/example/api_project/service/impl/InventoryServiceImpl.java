package com.example.api_project.service.impl;

import com.example.api_project.mapper.InventoryMapper;
import com.example.api_project.pojo.Inventory;
import com.example.api_project.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仓库(Inventory)表服务实现类
 *
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 不分页查询
     */
    public List<Inventory> querylist() {
        return inventoryMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param inventoryKey 主键
     * @return 实例对象
     */
    @Override
    public Inventory queryById(String inventoryKey) {
        return this.inventoryMapper.queryById(inventoryKey);
    }

    /**
     * 分页查询
     * @param startRows
     * @param pageSize
     * @param inventory 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Inventory inventory, Integer startRows, Integer pageSize) {
        long total = this.inventoryMapper.count(inventory);
        String inventoryName=inventory.getInventoryName();
        String inventoryCode=inventory.getInventoryCode();
        Integer  status=inventory.getStatus();
        List<Inventory> records;
        if(null!=inventory.getCategoryKey() && inventory.getCategoryKey().equals("all_WAREHOUSE")){
            String categoryKey="";
            records = this.inventoryMapper.queryAllByLimit(inventoryName,inventoryCode,status,categoryKey,startRows, pageSize);
        }else{
            String categoryKey=inventory.getCategoryKey();
            records = this.inventoryMapper.queryAllByLimit(inventoryName,inventoryCode,status,categoryKey,startRows, pageSize);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    @Override
    public Inventory insert(Inventory inventory) {
        this.inventoryMapper.insert(inventory);
        return inventory;
    }

    /**
     * 修改数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    @Override
    public Inventory update(Inventory inventory) {
        this.inventoryMapper.update(inventory);
        return this.queryById(inventory.getInventoryKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param inventory
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Inventory inventory) {
        return this.inventoryMapper.deleteById(inventory) > 0;
    }
}
