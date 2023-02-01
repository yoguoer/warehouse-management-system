package com.example.api_project.service.impl;

import com.example.api_project.mapper.SalesIntegrateMapper;
import com.example.api_project.service.SalesIntegrateService;
import com.example.api_project.vo.SalesIntegrate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 销售统计
 *
 */
@Service("SalesIntegrateService")
public class SalesIntegrateImpl implements SalesIntegrateService {
    @Autowired
    private SalesIntegrateMapper salesIntegrateMapper;

    /**
     * 分页查询
     *
     * @param salesIntegrate 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(SalesIntegrate salesIntegrate, Integer startRows, Integer pageSize) {
        String shopCode=salesIntegrate.getShopCode();
        String goodsCode=salesIntegrate.getGoodsCode();
        String customerCode=salesIntegrate.getCustomerCode();
        List<SalesIntegrate> records;
        long total;
        if(null!=shopCode){
            records = this.salesIntegrateMapper.queryAllByShop(shopCode,startRows, pageSize);
            total = this.salesIntegrateMapper.countByShop(salesIntegrate);
        }else if(null!=goodsCode){
            records = this.salesIntegrateMapper.queryAllByGoods(goodsCode,startRows, pageSize);
            total = this.salesIntegrateMapper.countByGoods(salesIntegrate);
        }else{
            records = this.salesIntegrateMapper.queryAllByCustomer(customerCode,startRows, pageSize);
            total = this.salesIntegrateMapper.countByCustomer(salesIntegrate);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

}
