package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.vo.SupplierInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.api_project.service.SupplierInventoryService;

import java.util.List;

/**
 * 供应商仓库配置对应表(SupplierInventory)表控制层
 *
 */
@RestController
@RequestMapping("api/SupplierInventory")
@CrossOrigin(origins = "*")

public class SupplierInventoryController {
    /**
     * 服务对象
     */
    @Autowired
    private SupplierInventoryService SupplierInventoryService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.SupplierInventoryService.querylist());
    }

    /**
     * 分页查询
     *
     * @param SupplierInventory 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(SupplierInventory SupplierInventory, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.SupplierInventoryService.queryByPage(SupplierInventory, startRows,pageSize));
    }

    /**
     * 新增数据
     *
     * @param SupplierInventory 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody SupplierInventory SupplierInventory) {
        return ResponseData.success(this.SupplierInventoryService.update(SupplierInventory));
    }

    /**
     * 编辑数据
     *
     * @param SupplierInventory 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody SupplierInventory SupplierInventory) {
        return ResponseData.success(this.SupplierInventoryService.update(SupplierInventory));
    }

    /**
     * 删除数据
     *
     * @param SupplierInventory
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody SupplierInventory SupplierInventory) {
        if(this.SupplierInventoryService.deleteById(SupplierInventory)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.SupplierInventoryService.deleteById(SupplierInventory));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<SupplierInventory> SupplierInventoryKeys) {
        for(SupplierInventory SupplierInventory: SupplierInventoryKeys){
            this.SupplierInventoryService.deleteById(SupplierInventory);
        }
        return ResponseData.success();
    }
}

