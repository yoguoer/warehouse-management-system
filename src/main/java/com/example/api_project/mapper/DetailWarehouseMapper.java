package com.example.api_project.mapper;

import com.example.api_project.pojo.DetailWarehouse;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 出入库明细表(DetailWarehouse)表数据库访问层
 *
 */
public interface DetailWarehouseMapper {

    DetailWarehouse getForKey(String shopCode,String goodsCode,String inputOutputKey);

    /**
     * 通过ID查询单条数据
     *
     * @param detailWarehouseKey 主键
     * @return 实例对象
     */
    DetailWarehouse queryById(String detailWarehouseKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<DetailWarehouse> queryAllByLimit(String goodsCode,String shopCode,Integer transType,Integer type,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param detailWarehouse 查询条件
     * @return 总行数
     */
    long count(DetailWarehouse detailWarehouse);

    /**
     * 新增数据
     *
     * @param detailWarehouse 实例对象
     * @return 影响行数
     */
    int insert(DetailWarehouse detailWarehouse);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DetailWarehouse> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DetailWarehouse> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DetailWarehouse> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<DetailWarehouse> entities);

    /**
     * 修改数据
     *
     * @param detailWarehouse 实例对象
     * @return 影响行数
     */
    int update(DetailWarehouse detailWarehouse);

    /**
     * 通过主键删除数据
     *
     * @param detailWarehouse
     * @return 影响行数
     */
    int deleteById(DetailWarehouse detailWarehouse);

}

