package com.example.api_project.mapper;

import com.example.api_project.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品表(Goods)表数据库访问层
 *
 */
public interface GoodsMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Goods> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param goodsKey 主键
     * @return 实例对象
     */
    Goods queryById(String goodsKey);

    /**
     * 查询指全部数据
     *
     * @param goods    查询条件
     * @return 对象列表
     */
    List<Goods> getListPage(Goods goods, Integer startRows, Integer pageSize);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(String goodsName, String categoryKey, String supplierKey,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param goods 查询条件
     * @return 总行数
     */
    long count(Goods goods);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int insert(Goods goods);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Goods> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Goods> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Goods> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Goods> entities);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param goods 主键
     * @return 影响行数
     */
    int deleteById(Goods goods);

}

