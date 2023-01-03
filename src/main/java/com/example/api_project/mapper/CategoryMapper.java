package com.example.api_project.mapper;

import com.example.api_project.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Pageable;
import java.util.List;

/**
 * 树状分类(Category)表数据库访问层
 *
 */
public interface CategoryMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Category> getList(String categoryType);

    /**
     * 通过ID查询单条数据
     *
     * @param categoryKey 主键
     * @return 实例对象
     */
    Category queryById(String categoryKey);

    //    根据id查分类名称
    String queryNameById(String categoryKey);

    /**
     * 查询指定行数据
     *
     * @param category 查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Category> queryAllByLimit(Category category, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param category 查询条件
     * @return 总行数
     */
    long count(Category category);

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 影响行数
     */
    int insert(Category category);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Category> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Category> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Category> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Category> entities);

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 影响行数
     */
    int update(Category category);

    /**
     * 通过主键删除数据
     *
     * @param category 主键
     * @return 影响行数
     */
    int deleteById(Category category);

}

