package com.example.api_project.service.impl;

import com.example.api_project.pojo.DetailWarehouse;
import com.example.api_project.mapper.DetailWarehouseMapper;
import com.example.api_project.service.DetailWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出入库明细表(DetailWarehouse)表服务实现类
 *
 */
@Service("detailWarehouseService")
public class DetailWarehouseServiceImpl implements DetailWarehouseService {
    @Autowired
    private DetailWarehouseMapper detailWarehouseMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param detailWarehouseKey 主键
     * @return 实例对象
     */
    @Override
    public DetailWarehouse queryById(String detailWarehouseKey) {
        return this.detailWarehouseMapper.queryById(detailWarehouseKey);
    }

    /**
     * 分页查询
     *
     * @param detailWarehouse 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(DetailWarehouse detailWarehouse, Integer startRows, Integer pageSize) {
        long total = this.detailWarehouseMapper.count(detailWarehouse);
        Integer transType=detailWarehouse.getTransType();
        Integer type=detailWarehouse.getType();
        String goodsCode=detailWarehouse.getGoodsCode();
        String shopCode=detailWarehouse.getShopCode();
        List<DetailWarehouse> records = this.detailWarehouseMapper.queryAllByLimit(goodsCode,shopCode,transType,type,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param detailWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public DetailWarehouse insert(DetailWarehouse detailWarehouse) {
        this.detailWarehouseMapper.insert(detailWarehouse);
        return detailWarehouse;
    }

    /**
     * 修改数据
     *
     * @param detailWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public DetailWarehouse update(DetailWarehouse detailWarehouse) {
        this.detailWarehouseMapper.update(detailWarehouse);
        return this.queryById(detailWarehouse.getDetailWarehouseKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param detailWarehouse
     * @return 是否成功
     */
    @Override
    public boolean deleteById(DetailWarehouse detailWarehouse) {
        return this.detailWarehouseMapper.deleteById(detailWarehouse) > 0;
    }
}
