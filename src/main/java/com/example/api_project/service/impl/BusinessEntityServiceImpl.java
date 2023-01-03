package com.example.api_project.service.impl;

import com.example.api_project.pojo.BusinessEntity;
import com.example.api_project.mapper.BusinessEntityMapper;
import com.example.api_project.service.BusinessEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务主体(BusinessEntity)表服务实现类
 *
 */
@Service("businessEntityService")
public class BusinessEntityServiceImpl implements BusinessEntityService {
    @Autowired
    private BusinessEntityMapper businessEntityMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param entityCode 主键
     * @return 实例对象
     */
    @Override
    public BusinessEntity queryById(String entityCode) {
        return this.businessEntityMapper.queryById(entityCode);
    }

    /**
     * 不分页查询
     */
    public List<BusinessEntity> querylist() {
        return businessEntityMapper.getList();
    }


    /**
     * 分页查询
     *
     * @param businessEntity 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(BusinessEntity businessEntity, Integer startRows, Integer pageSize) {
        long total = this.businessEntityMapper.count(businessEntity);
        String entityCode=businessEntity.getEntityCode();
        String entityName=businessEntity.getEntityName();
        List<BusinessEntity> records = businessEntityMapper.queryAllByLimit(entityCode,entityName,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param businessEntity 实例对象
     * @return 实例对象
     */
    @Override
    public BusinessEntity insert(BusinessEntity businessEntity) {
        this.businessEntityMapper.insert(businessEntity);
        return businessEntity;
    }

    /**
     * 修改数据
     *
     * @param businessEntity 实例对象
     * @return 实例对象
     */
    @Override
    public BusinessEntity update(BusinessEntity businessEntity) {
        this.businessEntityMapper.update(businessEntity);
        return this.queryById(businessEntity.getEntityCode());
    }

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    @Override
    public boolean deleteById(BusinessEntity businessEntity) {
        return this.businessEntityMapper.deleteById(businessEntity) >=0;
    }
}
