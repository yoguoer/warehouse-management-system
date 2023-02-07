package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * (Menu)实体类
 *
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = 532578601609128351L;
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

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
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

}

