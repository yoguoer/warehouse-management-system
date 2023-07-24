package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Menu;
import com.example.api_project.service.MenuService;
import com.example.api_project.vo.menuTree.menuTreeBuild;
import com.example.api_project.vo.menuTree.menuTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * (Menu)表控制层
 *
 */
@RestController
@RequestMapping("api/menu")
@CrossOrigin(origins = "*")

public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

    /**
     * 分页查询
     *
     * @return 查询结果
     */
    @GetMapping("/list")
    public Result getMenuList(Menu menu) {
        // 模拟测试数据（通常为数据库的查询结果）
        List<menuTreeNode> treeNodeList = new ArrayList<menuTreeNode>();
        List<menuTreeNode> firstMenu = new ArrayList<menuTreeNode>();
        List<menuTreeNode> subMenu = new ArrayList<menuTreeNode>();

        List<Menu> menuList = this.menuService.getMenuList(menu);

        for (Menu item : menuList) {
            treeNodeList.add(new menuTreeNode(item));
            if(item.getLevel()==0){
                firstMenu.add(new menuTreeNode(item));
            }else if(item.getLevel()==1){
                item.setParentMenuKey("");
                subMenu.add(new menuTreeNode(item));
            }else{
                subMenu.add(new menuTreeNode(item));
            }
        }
        // 创建树形结构（数据集合作为参数）
        menuTreeBuild treeBuild = new menuTreeBuild(treeNodeList);
        menuTreeBuild treeBuild1 = new menuTreeBuild(firstMenu);
        menuTreeBuild treeBuild2 = new menuTreeBuild(subMenu);

        // 原查询结果转换树形结构
        treeNodeList = treeBuild.buildTree();
        firstMenu = treeBuild1.buildTree();
        subMenu = treeBuild2.buildTree();
        // Result：个人封装返回的结果体
        Map<String,Object> res = new HashMap<>();
        res.put("menu",treeNodeList);
        res.put("firstMenu",firstMenu);
        res.put("subMenu",subMenu);
        return ResponseData.success(res);
//        return ResponseData.success(this.menuService.getMenuList(menu));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.menuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param menu 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(Menu menu) {
        return ResponseData.success(this.menuService.insert(menu));
    }

    /**
     * 编辑数据
     *
     * @param menu 实体
     * @return 编辑结果
     */
    @PutMapping("/update")
    public Result edit(Menu menu) {
        return ResponseData.success(this.menuService.update(menu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public Result deleteById(String id) {
        return ResponseData.success(this.menuService.deleteById(id));
    }

}

