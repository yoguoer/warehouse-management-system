package com.example.api_project.mapper;

import com.example.api_project.pojo.ReturnCheck;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 退货审批表(ReturnCheck)表数据库访问层
 *
 */
public interface ReturnCheckMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<ReturnCheck> getList();

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    ReturnCheck queryByIdIn(String inputOutputKey );

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    ReturnCheck queryByIdOut(String inputOutputKey );

    /**
     * 查询指定行数据
     *
     * @param checkStatus
     * @return 对象列表
     */
    List<ReturnCheck> queryAllByLimitInput(String shopCode,String goodsCode,String supplierCode,String inventoryCode,Integer checkStatus,Integer startRows, Integer pageSize);

    List<ReturnCheck> queryAllByLimitOutput(Integer checkStatus,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param returnCheck 查询条件
     * @return 总行数
     */
    long countInput(ReturnCheck returnCheck);

    long countOutput(ReturnCheck returnCheck);

    /**
     * 新增数据
     *
     * @param returnCheck 实例对象
     * @return 影响行数
     */
    int insert(ReturnCheck returnCheck);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ReturnCheck> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ReturnCheck> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ReturnCheck> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ReturnCheck> entities);

    /**
     * 修改数据
     *
     * @param returnCheck 实例对象
     * @return 影响行数
     */
    int update(ReturnCheck returnCheck);

    /**
     * 通过主键删除数据
     *
     * @param  returnCheck
     * @return 影响行数
     */
    int deleteById( ReturnCheck returnCheck);

}

