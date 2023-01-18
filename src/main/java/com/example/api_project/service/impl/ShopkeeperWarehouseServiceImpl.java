package com.example.api_project.service.impl;

import com.example.api_project.pojo.ShopkeeperWarehouse;
import com.example.api_project.mapper.ShopkeeperWarehouseMapper;
import com.example.api_project.service.ShopkeeperWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 零售商商品库存表(ShopkeeperWarehouse)表服务实现类
 *
 */
@Service("shopkeeperWarehouseService")
public class ShopkeeperWarehouseServiceImpl implements ShopkeeperWarehouseService {
    @Autowired
    private ShopkeeperWarehouseMapper shopkeeperWarehouseMapper;

    /**
     * 不分页查询
     */
    public List<ShopkeeperWarehouse> querylist() {
        return shopkeeperWarehouseMapper.getList();
    }

    /**
     * 通过门店编号和商品编号查找对应库存记录
     *
     */
    @Override
    public ShopkeeperWarehouse queryForKey(String shopCode,String goodsCode) {
        return this.shopkeeperWarehouseMapper.queryForKey(shopCode, goodsCode);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param shopkeeperWarehouseKey 主键
     * @return 实例对象
     */
    @Override
    public ShopkeeperWarehouse queryById(String shopkeeperWarehouseKey) {
        return this.shopkeeperWarehouseMapper.queryById(shopkeeperWarehouseKey);
    }

    /**
     * 分页查询
     *
     * @param shopkeeperWarehouse 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(ShopkeeperWarehouse shopkeeperWarehouse, Integer startRows, Integer pageSize) {
        long total = this.shopkeeperWarehouseMapper.count(shopkeeperWarehouse);
        String goodsCode=shopkeeperWarehouse.getGoodsCode();
        String shopCode=shopkeeperWarehouse.getShopCode();
        List<ShopkeeperWarehouse> records = this.shopkeeperWarehouseMapper.queryAllByLimit(goodsCode,shopCode, startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param shopkeeperWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public ShopkeeperWarehouse insert(ShopkeeperWarehouse shopkeeperWarehouse) {
        this.shopkeeperWarehouseMapper.insert(shopkeeperWarehouse);
        return shopkeeperWarehouse;
    }

    /**
     * 修改数据
     *
     * @param shopkeeperWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public ShopkeeperWarehouse update(ShopkeeperWarehouse shopkeeperWarehouse) {
        this.shopkeeperWarehouseMapper.update(shopkeeperWarehouse);
        return this.queryById(shopkeeperWarehouse.getShopkeeperWarehouseKey());
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteById(ShopkeeperWarehouse shopkeeperWarehouse) {
        return this.shopkeeperWarehouseMapper.deleteById(shopkeeperWarehouse) > 0;
    }
}
