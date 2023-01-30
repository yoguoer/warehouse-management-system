package com.example.api_project.service.impl;

import com.example.api_project.mapper.TransferIntegrateMapper;
import com.example.api_project.service.TransferIntegrateService;
import com.example.api_project.vo.TransferIntegrate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 销售统计
 *
 */
@Service("TransferIntegrateService")
public class TransferIntegrateImpl implements TransferIntegrateService {
    @Autowired
    private TransferIntegrateMapper transferIntegrateMapper;

    /**
     * 分页查询
     *
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(TransferIntegrate transferIntegrate, Integer startRows, Integer pageSize) {
        List<TransferIntegrate> records;
        Integer type=transferIntegrate.getType();
//        if(null!=type&&type==0){
//            String shopCode=transferIntegrate.getInShopCode();
//            records = this.transferIntegrateMapper.queryAllByShopIn(shopCode,startRows, pageSize);
//        }else if(null!=type&&type==1){
//            String shopCode=transferIntegrate.getOutShopCode();
//            records = this.transferIntegrateMapper.queryAllByShopOut(shopCode,startRows, pageSize);
//        }else{
            String goodsCode=transferIntegrate.getGoodsCode();
            String InShopCode=transferIntegrate.getInShopCode();
            String OutShopCode=transferIntegrate.getOutShopCode();
            records = this.transferIntegrateMapper.queryAllByGoods(goodsCode,InShopCode,OutShopCode,startRows, pageSize);
//        }
        long total = records.size();
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

}
