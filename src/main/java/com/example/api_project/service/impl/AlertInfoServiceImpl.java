package com.example.api_project.service.impl;

import com.example.api_project.pojo.AlertInfo;
import com.example.api_project.mapper.AlertInfoMapper;
import com.example.api_project.service.AlertInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预警信息表(AlertInfo)表服务实现类
 *
 */
@Service("alertInfoService")
public class AlertInfoServiceImpl implements AlertInfoService {
    @Resource
    private AlertInfoMapper alertInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param alertInfoKey 主键
     * @return 实例对象
     */
    @Override
    public AlertInfo queryById(String alertInfoKey) {
        return this.alertInfoMapper.queryById(alertInfoKey);
    }

    /**
     * 分页查询
     *
     * @param alertInfo 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(AlertInfo alertInfo, Integer startRows, Integer pageSize) {
        long total = this.alertInfoMapper.count(alertInfo);
        Integer type=alertInfo.getType();
        Integer status=alertInfo.getStatus();
        String shopSupplierKey=alertInfo.getShopSupplierKey();
        String goodsKey=alertInfo.getGoodsKey();
        List<AlertInfo> records = alertInfoMapper.queryAllByLimit(type,status,shopSupplierKey,goodsKey,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param alertInfo 实例对象
     * @return 实例对象
     */
    @Override
    public AlertInfo insert(AlertInfo alertInfo) {
        this.alertInfoMapper.insert(alertInfo);
        return alertInfo;
    }

    /**
     * 修改数据
     *
     * @param alertInfo 实例对象
     * @return 实例对象
     */
    @Override
    public AlertInfo update(AlertInfo alertInfo) {
        this.alertInfoMapper.update(alertInfo);
        return this.queryById(alertInfo.getAlertInfoKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param alertInfo
     * @return 是否成功
     */
    @Override
    public boolean deleteById(AlertInfo alertInfo) {
        return this.alertInfoMapper.deleteById(alertInfo) > 0;
    }
}
