package com.example.api_project.service.impl;

import com.example.api_project.pojo.InputWarehouse;
import com.example.api_project.mapper.InputWarehouseMapper;
import com.example.api_project.service.InputWarehouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入库表(InputWarehouse)表服务实现类
 *
 */
@Service("inputWarehouseService")
public class InputWarehouseServiceImpl implements InputWarehouseService {
    @Resource
    private InputWarehouseMapper inputWarehouseMapper;

    /**
     * 不分页查询
     */
    public List<InputWarehouse> querylist() {
        return inputWarehouseMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param inputWarehouseKey 主键
     * @return 实例对象
     */
    @Override
    public InputWarehouse queryById(String inputWarehouseKey) {
        return this.inputWarehouseMapper.queryById(inputWarehouseKey);
    }

    /**
     * 分页查询
     *
     * @param inputWarehouse 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(InputWarehouse inputWarehouse, Integer startRows, Integer pageSize) {
        long total = this.inputWarehouseMapper.count(inputWarehouse);
        String shopCode=inputWarehouse.getShopCode();
        String goodsCode=inputWarehouse.getGoodsCode();
        String supplierCode=inputWarehouse.getSupplierCode();
        String inventoryCode=inputWarehouse.getInventoryCode();
        Integer status=inputWarehouse.getStatus();
        Integer type=inputWarehouse.getType();
        List<InputWarehouse> records = this.inputWarehouseMapper.queryAllByLimit(shopCode,goodsCode,supplierCode,inventoryCode,status,type,startRows,  pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param inputWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public InputWarehouse insert(InputWarehouse inputWarehouse) {
        this.inputWarehouseMapper.insert(inputWarehouse);
        return inputWarehouse;
    }

    /**
     * 修改数据
     *
     * @param inputWarehouse 实例对象
     * @return 实例对象
     */
    @Override
    public InputWarehouse update(InputWarehouse inputWarehouse) {
        this.inputWarehouseMapper.update(inputWarehouse);
        return this.queryById(inputWarehouse.getInputWarehouseKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param inputWarehouse
     * @return 是否成功
     */
    @Override
    public boolean deleteById(InputWarehouse inputWarehouse) {
        return this.inputWarehouseMapper.deleteById(inputWarehouse) > 0;
    }
}
