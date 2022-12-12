package com.example.api_project.pojo;

import java.io.Serializable;

/**
 * 树状分类(Category)实体类
 *
 */
public class Category implements Serializable {
    private static final long serialVersionUID = -14138817361396869L;
    /**
     * 树状key
     */
    private String categoryKey;
    /**
     * 类型（SUPPLIER、GOODS、WAREHOUSE、CUSTOMER）
     */
    private String categoryType;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 父节点key
     */
    private String parentCategoryKey;
    /**
     * 节点名
     */
    private String categoryName;

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getParentCategoryKey() {
        return parentCategoryKey;
    }

    public void setParentCategoryKey(String parentCategoryKey) {
        this.parentCategoryKey = parentCategoryKey;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}

