package com.example.api_project.service.impl;

import com.example.api_project.mapper.*;
import com.example.api_project.pojo.Category;
import com.example.api_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 树状分类(Category)表服务实现类
 *
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 不分页查询
     */
    public List<Category> querylist(String categoryType) {
        return categoryMapper.getList(categoryType);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param categoryKey 主键
     * @return 实例对象
     */
    @Override
    public Category queryById(String categoryKey) {
        return this.categoryMapper.queryById(categoryKey);
    }

    /**
     * 新增数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    public Category insert(Category category) {
        this.categoryMapper.insert(category);
        return category;
    }

    /**
     * 修改数据
     *
     * @param category 实例对象
     * @return 实例对象
     */
    @Override
    public Category update(Category category) {
        this.categoryMapper.update(category);
        return this.queryById(category.getCategoryKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param category 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Category category) {
        return this.categoryMapper.deleteById(category) > 0;
    }
}
