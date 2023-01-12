package com.example.api_project.service.impl;

import com.example.api_project.pojo.TransferCheck;
import com.example.api_project.mapper.TransferCheckMapper;
import com.example.api_project.service.TransferCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 调货审批表(TransferCheck)表服务实现类
 *
 */
@Service("transferCheckService")
public class TransferCheckServiceImpl implements TransferCheckService {
    @Autowired
    private TransferCheckMapper transferCheckMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param transferCheckKey 主键
     * @return 实例对象
     */
    @Override
    public TransferCheck queryById(String transferCheckKey) {
        return this.transferCheckMapper.queryById(transferCheckKey);
    }

    /**
     * 分页查询
     *
     * @param transferCheck 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(TransferCheck transferCheck, Integer startRows, Integer pageSize) {
        long total;
        List<TransferCheck> records;
        String goodsCode=transferCheck.getGoodsCode();
        String inputShopCode=transferCheck.getInputShopCode();
        String outputShopCode=transferCheck.getOutputShopCode();
        Integer checkType=transferCheck.getCheckType();
        Integer checkStatus=transferCheck.getCheckStatus();
        if(checkType==0){
            total = this.transferCheckMapper.countIn(transferCheck);
            records = transferCheckMapper.queryAllByLimitIn(checkStatus,goodsCode,inputShopCode,outputShopCode,startRows, pageSize);
        }else{
            total = this.transferCheckMapper.countOut(transferCheck);
            records = transferCheckMapper.queryAllByLimitOut(checkStatus,goodsCode,inputShopCode,outputShopCode,startRows, pageSize);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param transferCheck 实例对象
     * @return 实例对象
     */
    @Override
    public TransferCheck insert(TransferCheck transferCheck) {
        this.transferCheckMapper.insert(transferCheck);
        return transferCheck;
    }

    /**
     * 修改数据
     *
     * @param transferCheck 实例对象
     * @return 实例对象
     */
    @Override
    public TransferCheck update(TransferCheck transferCheck) {
        this.transferCheckMapper.update(transferCheck);
        return this.queryById(transferCheck.getTransferCheckKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param transferCheck
     * @return 是否成功
     */
    @Override
    public boolean deleteById(TransferCheck transferCheck) {
        return this.transferCheckMapper.deleteById(transferCheck) > 0;
    }
}
