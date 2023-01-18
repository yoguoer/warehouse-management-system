package com.example.api_project.mapper;

import com.example.api_project.pojo.AlertInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 预警信息表(AlertInfo)表数据库访问层
 *
 */
public interface AlertInfoMapper {
    /**
     * 通过预警对象、商品、类型、状态来判断是否存在过
     *
     * @return 单条数据
     */
    AlertInfo queryByMany(String shopSupplierCode,String goodsCode,Integer status,Integer type);

    /**
     * 通过ID查询单条数据
     *
     * @param alertInfoKey 主键
     * @return 实例对象
     */
    AlertInfo queryById(String alertInfoKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<AlertInfo> queryAllByLimit(Integer type,Integer status,String shopSupplierCode,String goodsCode,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param alertInfo 查询条件
     * @return 总行数
     */
    long count(AlertInfo alertInfo);

    /**
     * 新增数据
     *
     * @param alertInfo 实例对象
     * @return 影响行数
     */
    int insert(AlertInfo alertInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AlertInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AlertInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AlertInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AlertInfo> entities);

    /**
     * 修改数据
     *
     * @param alertInfo 实例对象
     * @return 影响行数
     */
    int update(AlertInfo alertInfo);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(AlertInfo alertInfo);

}

