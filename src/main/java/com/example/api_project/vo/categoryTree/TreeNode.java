package com.example.api_project.vo.categoryTree;

import com.example.api_project.pojo.Category;

import java.util.List;

public class TreeNode {

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

    /** 子节点 */
    private List<TreeNode> children;

    public TreeNode(Category category) {
        this.categoryKey = category.getCategoryKey();
        this.categoryType = category.getCategoryType();
        this.level = category.getLevel();
        this.sort = category.getSort();
        this.createTime = category.getCreateTime();
        this.parentCategoryKey = category.getParentCategoryKey();
        this.categoryName = category.getCategoryName();
    }

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

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}