package com.example.api_project.vo.menuTree;


import com.example.api_project.pojo.Menu;

import java.util.List;

public class menuTreeNode {

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 父级菜单key
     */
    private String parentMenuKey;
    /**
     * 菜单key
     */
    private String menuKey;
    /**
     * 菜单可用角色权限
     */
    private String menuRole;
    /** 子节点 */
    private List<menuTreeNode> children;

    public menuTreeNode(Menu menu) {
        this.name = menu.getName();
        this.icon = menu.getIcon();
        this.componentName = menu.getComponentName();
        this.level = menu.getLevel();
        this.parentMenuKey = menu.getParentMenuKey();
        this.menuKey = menu.getMenuKey();
        this.menuRole = menu.getMenuRole();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getcomponentName() {
        return componentName;
    }

    public void setcomponentName(String componentName) {
        this.componentName = componentName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentMenuKey() {
        return parentMenuKey;
    }

    public void setParentMenuKey(String parentMenuKey) {
        this.parentMenuKey = parentMenuKey;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuRole() {
        return menuRole;
    }

    public void setMenuRole(String menuRole) {
        this.menuRole = menuRole;
    }

    public List<menuTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<menuTreeNode> children) {
        this.children = children;
    }
}