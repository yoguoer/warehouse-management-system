package com.example.api_project.service.impl;

import com.example.api_project.mapper.ShopMapper;
import com.example.api_project.pojo.Shop;
import com.example.api_project.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 门店表(Shop)表服务实现类
 *
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Resource
    private ShopMapper shopMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param shopKey 主键
     * @return 实例对象
     */
    @Override
    public Shop queryById(String shopKey) {
        return this.shopMapper.queryById(shopKey);
    }

    public Shop queryByshopCode(Shop shop) {
        String shopCode=shop.getShopCode();
        return this.shopMapper.queryByshopCode(shopCode);
    }
    /**
     * 不分页查询
     */
    public List<Shop> querylist() {
        return shopMapper.getList();
    }

    /**
     * 分页查询
     *
     * @param shop 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Shop shop, Integer startRows, Integer pageSize) {
        String shopName=shop.getShopName();
        Integer shopStatus=shop.getShopStatus();
        Integer cooperationType=shop.getCooperationType();
        List<Shop> records;
        if(null!=shop.getCategoryKey() && shop.getCategoryKey().equals("all_SHOP")){
            String categoryKey="";
            shop.setCategoryKey("");
            records = this.shopMapper.queryAllByLimit(shopName,categoryKey,shopStatus,cooperationType,startRows, pageSize);
        }else{
            String categoryKey=shop.getCategoryKey();
            records = this.shopMapper.queryAllByLimit(shopName,categoryKey,shopStatus,cooperationType,startRows, pageSize);
        }
        long total = this.shopMapper.count(shop);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    @Override
    public Shop insert(Shop shop) {
        this.shopMapper.insert(shop);
        return shop;
    }

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    @Override
    public Shop update(Shop shop) {
        this.shopMapper.update(shop);
        return this.queryById(shop.getShopKey());
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Shop shop) {
        return this.shopMapper.deleteById(shop) > 0;
    }
}
