package com.example.api_project.mapper;

import com.example.api_project.pojo.Brand;
import com.example.api_project.pojo.District;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域(District)表数据库访问层
 *
 */
public interface DistrictMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<District> getList(District district);

    /**
     * 查询指全部数据
     *
     * @param district    查询条件
     * @return 对象列表
     */
    List<District> getListPage(District district, Integer startRows, Integer pageSize);


    /**
     * 通过ID查询单条数据
     *
     * @param inventoryDistrictkey 主键
     * @return 实例对象
     */
    District queryById(String inventoryDistrictkey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<District> queryAllByLimit(String districtCode, String inventoryKey, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param district 查询条件
     * @return 总行数
     */
    long count(District district);

    /**
     * 新增数据
     *
     * @param district 实例对象
     * @return 影响行数
     */
    int insert(District district);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<District> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<District> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<District> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<District> entities);

    /**
     * 修改数据
     *
     * @param district 实例对象
     * @return 影响行数
     */
    int update(District district);

    /**
     * 通过主键删除数据
     *
     * @param district
     * @return 影响行数
     */
    int deleteById(District district);

}

