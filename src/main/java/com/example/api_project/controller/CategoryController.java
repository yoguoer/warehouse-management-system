package com.example.api_project.controller;

import com.example.api_project.vo.tree.TreeBuild;
import com.example.api_project.vo.tree.TreeNode;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Category;
import com.example.api_project.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 树状分类(Category)表控制层
 *
 */
@RestController
@RequestMapping("api/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 不分页查询
     */
    @GetMapping("/tree")
    public Result querylist(String categoryType) {
        // 模拟测试数据（通常为数据库的查询结果）
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        List<Category> categoryList = this.categoryService.querylist(categoryType);
        for (Category category : categoryList) {
            treeNodeList.add(new TreeNode(category));
        }
        // 创建树形结构（数据集合作为参数）
        TreeBuild treeBuild = new TreeBuild(treeNodeList);
        // 原查询结果转换树形结构
        treeNodeList = treeBuild.buildTree();
        // Result：个人封装返回的结果体
        return ResponseData.success(treeNodeList);
//        return ResponseData.success(this.categoryService.querylist(categoryType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param categoryKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("categoryKey") String categoryKey) {
        return ResponseData.success(this.categoryService.queryById(categoryKey));
    }

    /**
     * 新增数据
     *
     * @param category 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        category.setCategoryKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.categoryService.insert(category));
    }

    /**
     * 编辑数据
     *
     * @param category 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Category category) {
        return ResponseData.success(this.categoryService.update(category));
    }

    /**
     * 删除数据
     *
     * @param category 主键
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody Category category) {
        return ResponseData.success(this.categoryService.deleteById(category));
    }

}

