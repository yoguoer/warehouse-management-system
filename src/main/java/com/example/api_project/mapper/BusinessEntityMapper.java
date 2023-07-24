package com.example.api_project.mapper;

import com.example.api_project.pojo.BusinessEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 业务主体(BusinessEntity)表数据库访问层
 *
 */
public interface BusinessEntityMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param entityCode 主键
     * @return 实例对象
     */
    BusinessEntity queryById(String entityCode);

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<BusinessEntity> getList();

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<BusinessEntity> queryAllByLimit(String entityCode, String entityName,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param businessEntity 查询条件
     * @return 总行数
     */
    long count(BusinessEntity businessEntity);

    /**
     * 新增数据
     *
     * @param businessEntity 实例对象
     * @return 影响行数
     */
    int insert(BusinessEntity businessEntity);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BusinessEntity> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BusinessEntity> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BusinessEntity> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<BusinessEntity> entities);

    /**
     * 修改数据
     *
     * @param businessEntity 实例对象
     * @return 影响行数
     */
    int update(BusinessEntity businessEntity);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(BusinessEntity businessEntity);

}

