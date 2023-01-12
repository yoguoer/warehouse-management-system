package com.example.api_project.mapper;

import com.example.api_project.pojo.TransferCheck;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 调货审批表(TransferCheck)表数据库访问层
 *
 */
public interface TransferCheckMapper {

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    TransferCheck queryById(String transferCheckKey);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<TransferCheck> queryAllByLimitIn(String goodsCode,String inputShopCode,String outputShopCode, Integer startRows, Integer pageSize);
    List<TransferCheck> queryAllByLimitOut(String goodsCode,String inputShopCode,String outputShopCode, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param transferCheck 查询条件
     * @return 总行数
     */
    long countIn(TransferCheck transferCheck);
    long countOut(TransferCheck transferCheck);

    /**
     * 新增数据
     *
     * @param transferCheck 实例对象
     * @return 影响行数
     */
    int insert(TransferCheck transferCheck);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TransferCheck> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<TransferCheck> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TransferCheck> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TransferCheck> entities);

    /**
     * 修改数据
     *
     * @param transferCheck 实例对象
     * @return 影响行数
     */
    int update(TransferCheck transferCheck);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById(TransferCheck transferCheck);

}

