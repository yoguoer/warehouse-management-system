package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Inventory;
import com.example.api_project.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 仓库(Inventory)表控制层
 *
 */
@RestController
@RequestMapping("api/inventory")
@CrossOrigin(origins = "*")

public class InventoryController {
    /**
     * 服务对象
     */
    @Autowired
    private InventoryService inventoryService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.inventoryService.querylist());
    }

    /**
     * 分页查询
     *
     * @param inventory   筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Inventory inventory, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.inventoryService.queryByPage(inventory,startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param inventoryKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("inventoryKey") String inventoryKey) {
        return ResponseData.success(this.inventoryService.queryById(inventoryKey));
    }

    /**
     * 新增数据
     *
     * @param inventory 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Inventory inventory) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        inventory.setInventoryKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.inventoryService.insert(inventory));
    }

    /**
     * 编辑数据
     *
     * @param inventory 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Inventory inventory) {
        return ResponseData.success(this.inventoryService.update(inventory));
    }

    /**
     * 删除数据
     *
     * @param inventory 主键
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody Inventory inventory) {
        if(this.inventoryService.deleteById(inventory)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.inventoryService.deleteById(inventory));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Inventory> inventoryKeys) {
        for(Inventory inventory: inventoryKeys){
            this.inventoryService.deleteById(inventory);
        }
        return ResponseData.success();
    }
}

