package com.example.api_project.mapper;

import com.example.api_project.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品牌(Brand)表数据库访问层
 */
public interface BrandMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param brandKey 主键
     * @return 实例对象
     */
    Brand queryById(String brandKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Brand> queryAllByLimit(String brandCode,String brandName, Integer startRows, Integer pageSize);

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Brand> getList();

    /**
     * 查询指全部数据
     *
     * @param brand    查询条件
     * @return 对象列表
     */
    List<Brand> getListPage(Brand brand, Integer startRows, Integer pageSize);


    /**
     * 统计总行数
     *
     * @param brand 查询条件
     * @return 总行数
     */
    long count(Brand brand);

    /**
     * 新增数据
     *
     * @param brand 实例对象
     * @return 影响行数
     */
    int insert(Brand brand);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Brand> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Brand> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Brand> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Brand> entities);

    /**
     * 修改数据
     *
     * @param brand 实例对象
     * @return 影响行数
     */
    int update(Brand brand);

    /**
     * 通过主键删除数据
     *
     * @param brand
     * @return 影响行数
     */
    int deleteById(Brand brand);

}

