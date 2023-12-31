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

    public boolean setNoCategory(String categoryKey){
        List<Inventory> inventoryList= this.inventoryMapper.getListCategory(categoryKey);
        boolean flag = false;
        for(Inventory inventory:inventoryList){
            inventory.setCategoryKey("NOCATEGORY_WAREHOUSE");
            flag=this.inventoryMapper.setNoCategory(inventory);
        }
        return flag;
    }

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
        String inventoryName=inventory.getInventoryName();
        String inventoryCode=inventory.getInventoryCode();
        Integer status=inventory.getStatus();
        String belongKey=inventory.getBelongKey();
        Integer inventoryType=inventory.getInventoryType();
        List<Inventory> records;
        if(null!=inventory.getCategoryKey() && inventory.getCategoryKey().equals("all_WAREHOUSE")){
            String categoryKey="";
            inventory.setCategoryKey("");
            records = this.inventoryMapper.queryAllByLimit(inventoryType,inventoryName,inventoryCode,status,categoryKey,belongKey,startRows, pageSize);
        }else{
            String categoryKey=inventory.getCategoryKey();
            records = this.inventoryMapper.queryAllByLimit(inventoryType,inventoryName,inventoryCode,status,categoryKey,belongKey,startRows, pageSize);
        }
        long total = this.inventoryMapper.count(inventory);
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
