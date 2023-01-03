package com.example.api_project.service.impl;

import com.example.api_project.mapper.DistrictMapper;
import com.example.api_project.pojo.District;
import com.example.api_project.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域(District)表服务实现类
 *
 */
@Service("districtService")
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    /**
     * 不分页查询
     */
    public List<District> querylist(District district) {
        return districtMapper.getList(district);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param inventoryDistrictkey 主键
     * @return 实例对象
     */
    @Override
    public District queryById(String inventoryDistrictkey) {
        return this.districtMapper.queryById(inventoryDistrictkey);
    }

    /**
     * 分页查询
     *
     * @param district    筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(District district, Integer startRows, Integer pageSize) {
        long total = this.districtMapper.count(district);
        String districtCode=district.getDistrictCode();
        String districtName=district.getDistrictName();
        Integer status=district.getStatus();
        String inventoryKey=district.getInventoryKey();
        List<District> records = districtMapper.queryAllByLimit(districtCode,districtName,status,inventoryKey,startRows, pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param district 实例对象
     * @return 实例对象
     */
    @Override
    public District insert(District district) {
        this.districtMapper.insert(district);
        return district;
    }

    /**
     * 修改数据
     *
     * @param district 实例对象
     * @return 实例对象
     */
    @Override
    public District update(District district) {
        this.districtMapper.update(district);
        return this.queryById(district.getInventoryDistrictkey());
    }

    /**
     * 通过主键删除数据
     *
     * @param district
     * @return 是否成功
     */
    @Override
    public boolean deleteById(District district) {
        return this.districtMapper.deleteById(district) > 0;
    }
}
