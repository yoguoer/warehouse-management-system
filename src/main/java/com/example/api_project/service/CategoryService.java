package com.example.api_project.service;

import com.example.api_project.pojo.Category;

import java.util.List;

/**
 * 树状分类(Category)表服务接口
 *
 */
public interface CategoryService {

    /**
     * 不分页查询
     */
    List<Category> querylist(String categoryType);

    /**
     * 通过ID查询单条数据
     *
     * @param categoryKey 主键
     * @return 实例对象
     */
    Category queryById(String categoryKey);

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    Category insert(Category category);

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    Category update(Category category);

    /**
     * 通过主键删除数据
     *
     * @param category 主键
     * @return 是否成功
     */
    boolean deleteById(Category category);

}
