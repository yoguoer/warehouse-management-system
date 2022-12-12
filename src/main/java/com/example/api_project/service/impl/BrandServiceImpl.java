package com.example.api_project.service.impl;

import com.example.api_project.mapper.BrandMapper;
import com.example.api_project.pojo.Brand;
import com.example.api_project.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌(Brand)表服务实现类
 */
@Service("brandService")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param brandKey 主键
     * @return 实例对象
     */
    @Override
    public Brand queryById(String brandKey) {
        return brandMapper.queryById(brandKey);
    }

    /**
     * 不分页查询
     */
    public List<Brand> querylist() {
        return brandMapper.getList();
    }

    /**
     * 分页查询
     *
     * @param brand       筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Brand brand, Integer startRows, Integer pageSize) {
        long total = brandMapper.count(brand);
        String brandCode=brand.getBrandCode();
        String brandName=brand.getBrandName();
        List<Brand> records = brandMapper.queryAllByLimit(brandCode,brandName,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param brand 实例对象
     * @return 实例对象
     */
    @Override
    public Brand insert(Brand brand) {
        brandMapper.insert(brand);
        return brand;
    }

    /**
     * 修改数据
     *
     * @param brand 实例对象
     * @return 实例对象
     */
    @Override
    public Brand update(Brand brand) {
        brandMapper.update(brand);
        return queryById(brand.getBrandKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param brand 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Brand brand) {
        return brandMapper.deleteById(brand)>=0;
    }
}
