package com.example.api_project.service.impl;

import com.example.api_project.pojo.OutputWarehouse;
import com.example.api_project.mapper.OutputWarehouseMapper;
import com.example.api_project.service.OutputWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库表(OutputWarehouse)表服务实现类
 *
 */
@Service("outputWarehouseService")
public class OutputWarehouseServiceImpl implements OutputWarehouseService {
    @Autowired
    private OutputWarehouseMapper outputWarehouseMapper;

    /**
     * 不分页查询
     */
    public List<OutputWarehouse> querylist() {
        return outputWarehouseMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param outputWarehouseKey 主键
     * @return 实例对象
     */
    @Override
    public OutputWarehouse queryById(String outputWarehouseKey) {
        return this.outputWarehouseMapper.queryById(outputWarehouseKey);
    }

    /**
     * 分页查询
     *
     * @param outputWarehouse 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(OutputWarehouse outputWarehouse, Integer startRows, Integer pageSize) {
        String shopCode=outputWarehouse.getShopCode();
        String goodsCode=outputWarehouse.getGoodsCode();
        String customerCode=outputWarehouse.getCustomerCode();
        String inventoryCode=outputWarehouse.getInventoryCode();
        Integer status=outputWarehouse.getStatus();
        Integer type=outputWarehouse.getType();
        Integer isDeleted=outputWarehouse.getIsDeleted();
        List<OutputWarehouse> records = this.outputWarehouseMapper.queryAllByLimit(shopCode,goodsCode,customerCode,inventoryCode,status,type,isDeleted,startRows,  pageSize);
        long total = this.outputWarehouseMapper.count(outputWarehouse);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param outputWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public OutputWarehouse insert(OutputWarehouse outputWarehouse) {
        this.outputWarehouseMapper.insert(outputWarehouse);
        return outputWarehouse;
    }

    /**
     * 修改数据
     *
     * @param outputWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public OutputWarehouse update(OutputWarehouse outputWarehouse) {
        this.outputWarehouseMapper.update(outputWarehouse);
        return this.queryById(outputWarehouse.getOutputWarehouseKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param outputWarehouse
     * @return 是否成功
     */
    @Override
    public boolean deleteById(OutputWarehouse outputWarehouse) {
        return this.outputWarehouseMapper.deleteById(outputWarehouse) > 0;
    }
}
