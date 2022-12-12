package com.example.api_project.service;

import com.example.api_project.pojo.Brand;
import com.example.api_project.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 品牌(Brand)表服务接口
 *
 * @author makejava
 * @since 2022-11-19 00:34:56
 */
public interface BrandService {

    /**
     * 通过ID查询单条数据
     *
     * @param brandKey 主键
     * @return 实例对象
     */
    Brand queryById(String brandKey);

    /**
     * 不分页查询
     */
    List<Brand> querylist();

    /**
     * 分页查询
     *
     * @param brand     筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Brand brand, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param brand 实例对象
     * @return 实例对象
     */
    Brand insert(Brand brand);

    /**
     * 修改数据
     *
     * @param brand 实例对象
     * @return 实例对象
     */
    Brand update(Brand brand);

    /**
     * 通过主键删除数据
     *
     * @param brand
     * @return 是否成功
     */
    boolean deleteById(Brand brand);

}
