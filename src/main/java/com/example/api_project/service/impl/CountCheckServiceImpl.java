package com.example.api_project.service.impl;

import com.example.api_project.mapper.GoodsMapper;
import com.example.api_project.mapper.ShopMapper;
import com.example.api_project.pojo.CountCheck;
import com.example.api_project.mapper.CountCheckMapper;
import com.example.api_project.service.CountCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 盘亏盘盈审批表(CountCheck)表服务实现类
 *
 */
@Service("countCheckService")
public class CountCheckServiceImpl implements CountCheckService {
    @Autowired
    private CountCheckMapper countCheckMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 不分页查询
     */
    public List<CountCheck> querylist() {
        return this.countCheckMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param  countCheckKey
     * @return 实例对象
     */
    @Override
    public CountCheck queryById(String countCheckKey) {
        return this.countCheckMapper.queryById(countCheckKey);
    }

    /**
     * 分页查询
     *
     * @param countCheck 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(CountCheck countCheck, Integer startRows, Integer pageSize) {
        Integer checkStatus=countCheck.getCheckStatus();
        Integer checkType=countCheck.getCheckType();
        String checkPeople=countCheck.getCheckPeople();
        String shopCode=countCheck.getShopCode();
        String goodsCode=countCheck.getGoodsCode();
        List<CountCheck> records = this.countCheckMapper.queryAllByLimit(shopCode,goodsCode,checkStatus,checkType,checkPeople,startRows, pageSize);
        if(!records.isEmpty()){
            for(CountCheck item:records){
                System.out.println(item.getShopCode());
                System.out.println(item.getGoodsCode());

                if(item.getGoodsCode()!=""){
                    goodsCode=item.getGoodsCode();
                    String goodsName= this.goodsMapper.getNamelyCode(goodsCode);
                    item.setGoodsName(goodsName);
                }
                if(item.getShopCode()!=""){
                    shopCode=item.getShopCode();
                    String shopName=this.shopMapper.getNamelyCode(shopCode);
                    item.setShopName(shopName);
                }
            }
        }
        long total = this.countCheckMapper.count(countCheck);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param countCheck 实例对象
     * @return 实例对象
     */
    @Override
    public CountCheck insert(CountCheck countCheck) {
        this.countCheckMapper.insert(countCheck);
        return countCheck;
    }

    /**
     * 修改数据
     *
     * @param countCheck 实例对象
     * @return 实例对象
     */
    @Override
    public CountCheck update(CountCheck countCheck) {
        this.countCheckMapper.update(countCheck);
        return this.queryById(countCheck.getCountCheckKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param  countCheck
     * @return 是否成功
     */
    @Override
    public boolean deleteById(CountCheck countCheck) {
        return this.countCheckMapper.deleteById(countCheck) > 0;
    }
}
