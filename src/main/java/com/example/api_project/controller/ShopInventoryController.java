package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.service.ShopInventoryService;
import com.example.api_project.vo.ShopInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门店仓库配置对应表(ShopInventory)表控制层
 *
 */
@RestController
@RequestMapping("api/ShopInventory")
public class ShopInventoryController {
    /**
     * 服务对象
     */
    @Autowired
    private ShopInventoryService ShopInventoryService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist(ShopInventory ShopInventory) {
        return ResponseData.success(this.ShopInventoryService.querylist(ShopInventory));
    }

    /**
     * 分页查询
     *
     * @param ShopInventory 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(ShopInventory ShopInventory, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.ShopInventoryService.queryByPage(ShopInventory, startRows,pageSize));
    }

    /**
     * 新增数据
     *
     * @param ShopInventory 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody ShopInventory ShopInventory) {
        return ResponseData.success(this.ShopInventoryService.update(ShopInventory));
    }

    /**
     * 编辑数据
     *
     * @param ShopInventory 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody ShopInventory ShopInventory) {
        return ResponseData.success(this.ShopInventoryService.update(ShopInventory));
    }

    /**
     * 删除数据
     *
     * @param ShopInventory
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody ShopInventory ShopInventory) {
        if(this.ShopInventoryService.deleteById(ShopInventory)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.ShopInventoryService.deleteById(ShopInventory));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<ShopInventory> ShopInventoryKeys) {
        for(ShopInventory ShopInventory: ShopInventoryKeys){
            this.ShopInventoryService.deleteById(ShopInventory);
        }
        return ResponseData.success();
    }

    /**
     * 通过(shopKey)查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/getByshopCode")
    public Result queryByshopCode(ShopInventory ShopInventory) {
        return ResponseData.success(this.ShopInventoryService.queryByshopCode(ShopInventory));
    }
}

