package com.example.api_project.service.impl;

import com.example.api_project.mapper.GoodsMapper;
import com.example.api_project.pojo.Goods;
import com.example.api_project.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品表(Goods)表服务实现类
 *
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public boolean setNoCategory(String categoryKey){
        List<Goods> goodsList= this.goodsMapper.getListCategory(categoryKey);
        boolean flag = false;
        for(Goods goods:goodsList){
            goods.setCategoryKey("NOCATEGORY_GOODS");
            flag=this.goodsMapper.setNoCategory(goods);
        }
        return flag;
    }

    /**
     * 不分页查询
     */
    public List<Goods> querylist() {
        return goodsMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param goodsKey 主键
     * @return 实例对象
     */
    @Override
    public Goods queryById(String goodsKey) {
        return this.goodsMapper.queryById(goodsKey);
    }

    /**
     * 分页查询
     *
     * @param goods 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Goods goods, Integer startRows, Integer pageSize) {
        String goodsCode=goods.getGoodsCode();
        String goodsName=goods.getGoodsName();
        String supplierKey=goods.getSupplierKey();
        String brandCode=goods.getBrandCode();
        Integer state=goods.getState();
        List<Goods> records;
        if(null!=goods.getCategoryKey() && goods.getCategoryKey().equals("all_GOODS")){
            String categoryKey="";
            goods.setCategoryKey("");
            records = this.goodsMapper.queryAllByLimit(brandCode,state,goodsCode,goodsName,categoryKey,supplierKey,startRows, pageSize);
        }else{
            String categoryKey=goods.getCategoryKey();
            records = this.goodsMapper.queryAllByLimit(brandCode,state,goodsCode,goodsName,categoryKey,supplierKey,startRows, pageSize);
        }
//        String categoryKey=goods.getCategoryKey();
//        List<Goods> records = this.goodsMapper.queryAllByLimit(goodsName,categoryKey,startRows, pageSize);
        long total = this.goodsMapper.count(goods);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
        }

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Goods insert(Goods goods) {
        this.goodsMapper.insert(goods);
        return goods;
    }

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Goods update(Goods goods) {
        this.goodsMapper.update(goods);
        return this.queryById(goods.getGoodsKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param goods 实例对象
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Goods goods) {
        return this.goodsMapper.deleteById(goods) > 0;
    }
}
