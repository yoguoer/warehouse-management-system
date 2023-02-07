package com.example.api_project.service;

import com.example.api_project.pojo.Menu;

import java.util.List;

/**
 * (Menu)表服务接口
 *
 */
public interface MenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuKey 主键
     * @return 实例对象
     */
    Menu queryById(String menuKey);

    /**
     * 分页查询
     *
     * @param menu 筛选条件=
     * @return 查询结果
     */
    List<Menu> getMenuList(Menu menu);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    Menu insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    Menu update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param menuKey 主键
     * @return 是否成功
     */
    boolean deleteById(String menuKey);

}
