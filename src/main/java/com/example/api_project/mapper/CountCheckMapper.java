package com.example.api_project.mapper;

import com.example.api_project.pojo.CountCheck;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 盘亏盘盈审批表(CountCheck)表数据库访问层
 *
 */
public interface CountCheckMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<CountCheck> getList();

    /**
     * 通过ID查询单条数据
     *
     * @param  countCheckKey
     * @return 实例对象
     */
    CountCheck queryById(String countCheckKey);

    /**
     * 查询指定行数据
     *
     * @param countCheck 查询条件
     * @return 对象列表
     */
    List<CountCheck> queryAllByLimit(CountCheck countCheck, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param countCheck 查询条件
     * @return 总行数
     */
    long count(CountCheck countCheck);

    /**
     * 新增数据
     *
     * @param countCheck 实例对象
     * @return 影响行数
     */
    int insert(CountCheck countCheck);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<CountCheck> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<CountCheck> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<CountCheck> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<CountCheck> entities);

    /**
     * 修改数据
     *
     * @param countCheck 实例对象
     * @return 影响行数
     */
    int update(CountCheck countCheck);

    /**
     * 通过主键删除数据
     *
     * @param  countCheck
     * @return 影响行数
     */
    int deleteById(CountCheck countCheck);

}

