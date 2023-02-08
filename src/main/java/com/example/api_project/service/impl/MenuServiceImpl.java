package com.example.api_project.service.impl;

import com.example.api_project.pojo.Menu;
import com.example.api_project.mapper.MenuMapper;
import com.example.api_project.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Menu)表服务实现类
 *
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param menuKey 主键
     * @return 实例对象
     */
    @Override
    public Menu queryById(String menuKey) {
        return this.menuMapper.queryById(menuKey);
    }

    /**
     * 分页查询
     *
     * @param menu 筛选条件
     * @return 查询结果
     */
    @Override
    public List<Menu> getMenuList(Menu menu) {
        String menuRole=menu.getMenuRole();
        return this.menuMapper.getMenuList(menuRole);
    }

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu insert(Menu menu) {
        this.menuMapper.insert(menu);
        return menu;
    }

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 实例对象
     */
    @Override
    public Menu update(Menu menu) {
        this.menuMapper.update(menu);
        return this.queryById(menu.getMenuKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuKey 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String menuKey) {
        return this.menuMapper.deleteById(menuKey) > 0;
    }
}
