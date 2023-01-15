package com.example.api_project.service.impl;

import com.example.api_project.mapper.InputWarehouseMapper;
import com.example.api_project.mapper.OutputWarehouseMapper;
import com.example.api_project.pojo.InputWarehouse;
import com.example.api_project.pojo.OutputWarehouse;
import com.example.api_project.pojo.ReturnCheck;
import com.example.api_project.mapper.ReturnCheckMapper;
import com.example.api_project.service.ReturnCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退货审批表(ReturnCheck)表服务实现类
 *
 */
@Service("returnCheckService")
public class ReturnCheckServiceImpl implements ReturnCheckService {
    @Autowired
    private ReturnCheckMapper returnCheckMapper;

    @Autowired
    private InputWarehouseMapper inputWarehouseMapper;

    @Autowired
    private OutputWarehouseMapper outputWarehouseMapper;

    /**
     * 不分页查询
     */
    public List<ReturnCheck> querylist() {
        return returnCheckMapper.getList();
    }
    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    @Override
    public ReturnCheck queryById(ReturnCheck returnCheck) {
        String inputOutputKey=returnCheck.getInputOutputKey();
        Integer checkType=returnCheck.getCheckType();
        if(checkType==0){
            return this.returnCheckMapper.queryByIdIn(inputOutputKey);
        }else{
            return this.returnCheckMapper.queryByIdOut(inputOutputKey);
        }
    }

    /**
     * 分页查询
     *
     * @param returnCheck 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(ReturnCheck returnCheck, Integer startRows, Integer pageSize) {
        long total;
        Integer checkStatus=returnCheck.getCheckStatus();
        Integer checkType=returnCheck.getCheckType();
        List<ReturnCheck> records;
        if(checkType==0){
            String shopCode=returnCheck.getShopCode();
            String goodsCode=returnCheck.getGoodsCode();
            String supplierCode=returnCheck.getSupplierCode();
            String inventoryCode=returnCheck.getInventoryCode();
            total = this.returnCheckMapper.countInput(returnCheck);
            records = returnCheckMapper.queryAllByLimitInput(shopCode,goodsCode,supplierCode,inventoryCode,checkStatus,startRows, pageSize);
        }else{
            total = this.returnCheckMapper.countOutput(returnCheck);
            records = returnCheckMapper.queryAllByLimitOutput(checkStatus,startRows, pageSize);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param returnCheck 实例对象
     * @return 实例对象
     */
    @Override
    public ReturnCheck insert(ReturnCheck returnCheck) {
        if(returnCheck.getCheckType()==0){
            InputWarehouse inputWarehouse=returnCheck.getInputWarehouse();
            this.inputWarehouseMapper.update(inputWarehouse);
        }else if(returnCheck.getCheckType()==1){
            OutputWarehouse outputWarehouse=returnCheck.getOutputWarehouse();
            this.outputWarehouseMapper.update(outputWarehouse);
        }
        this.returnCheckMapper.insert(returnCheck);
        return returnCheck;
    }

    /**
     * 修改数据
     *
     * @param returnCheck 实例对象
     * @return 实例对象
     */
    @Override
    public ReturnCheck update(ReturnCheck returnCheck) {
        Integer checkType=returnCheck.getCheckType();
        if(checkType==0){
            InputWarehouse inputWarehouse=returnCheck.getInputWarehouse();
            this.inputWarehouseMapper.update(inputWarehouse);
        }else if(checkType==1){
            OutputWarehouse outputWarehouse=returnCheck.getOutputWarehouse();
            this.outputWarehouseMapper.update(outputWarehouse);
        }
        this.returnCheckMapper.update(returnCheck);
        return this.queryById(returnCheck);
    }

    /**
     * 通过主键删除数据
     *
     * @param  returnCheck
     * @return 是否成功
     */
    @Override
    public boolean deleteById( ReturnCheck returnCheck) {
        return this.returnCheckMapper.deleteById(returnCheck) > 0;
    }
}
