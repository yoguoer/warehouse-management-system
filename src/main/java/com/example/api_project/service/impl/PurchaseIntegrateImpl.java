package com.example.api_project.service.impl;

import com.example.api_project.mapper.PurchaseIntegrateMapper;
import com.example.api_project.service.PurchaseIntegrateService;
import com.example.api_project.vo.PurchaseIntegrate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 销售统计
 *
 */
@Service("PurchaseIntegrateService")
public class PurchaseIntegrateImpl implements PurchaseIntegrateService {
    @Autowired
    private PurchaseIntegrateMapper purchaseIntegrateMapper;

    /**
     * 分页查询
     *
     * @param purchaseIntegrate 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(PurchaseIntegrate purchaseIntegrate, Integer startRows, Integer pageSize) {
        String shopCode=purchaseIntegrate.getShopCode();
        String goodsCode=purchaseIntegrate.getGoodsCode();
        String supplierCode=purchaseIntegrate.getSupplierCode();
        List<PurchaseIntegrate> records;
        Integer total;
        if(null!=shopCode){
            records = this.purchaseIntegrateMapper.queryAllByShop(shopCode,startRows, pageSize);
            total = this.purchaseIntegrateMapper.countByShop(purchaseIntegrate);
        }else if(null!=goodsCode){
            records = this.purchaseIntegrateMapper.queryAllByGoods(goodsCode,startRows, pageSize);
            total = this.purchaseIntegrateMapper.countByGoods(purchaseIntegrate);
        }else{
            total = this.purchaseIntegrateMapper.countBySupplier(purchaseIntegrate);
            records = this.purchaseIntegrateMapper.queryAllBySupplier(supplierCode,startRows, pageSize);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

}
