package com.example.api_project.service;

import com.example.api_project.pojo.District;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 区域(District)表服务接口
 *
 */
public interface DistrictService {

    /**
     * 不分页查询
     */
    List<District> querylist(District district);

    /**
     * 通过ID查询单条数据
     *
     * @param inventoryDistrictkey 主键
     * @return 实例对象
     */
    District queryById(String inventoryDistrictkey);

    /**
     * 分页查询
     *
     * @param district    筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(District district, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param district 实例对象
     * @return 实例对象
     */
    District insert(District district);

    /**
     * 修改数据
     *
     * @param district 实例对象
     * @return 实例对象
     */
    District update(District district);

    /**
     * 通过主键删除数据
     *
     * @param district
     * @return 是否成功
     */
    boolean deleteById(District district);

}
