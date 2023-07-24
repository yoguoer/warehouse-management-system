package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.ShopkeeperWarehouse;
import com.example.api_project.service.ShopkeeperWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 零售商商品库存表(ShopkeeperWarehouse)表控制层
 *
 */
@RestController
@RequestMapping("api/shopkeeperWarehouse")
@CrossOrigin(origins = "*")

public class ShopkeeperWarehouseController {
    /**
     * 服务对象
     */
    @Autowired
    private ShopkeeperWarehouseService shopkeeperWarehouseService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.shopkeeperWarehouseService.querylist());
    }

    /**
     * 分页查询
     *
     * @param shopkeeperWarehouse 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(ShopkeeperWarehouse shopkeeperWarehouse, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.shopkeeperWarehouseService.queryByPage(shopkeeperWarehouse,startRows,pageSize));
    }

    /**
     * 通过门店编号和商品编号查找对应库存记录
     *
     * @return 单条数据
     */
    @GetMapping("/queryForKey")
    public Result queryForKey(String shopCode,String goodsCode) {
        return ResponseData.success(this.shopkeeperWarehouseService.queryForKey(shopCode,goodsCode));
    }
    /**
     * 通过shopCode查询单条数据
     *
     */
    @GetMapping("/getByShopCode")
    public Result getByShopCode(ShopkeeperWarehouse shopkeeperWarehouse) {
        return ResponseData.success(this.shopkeeperWarehouseService.getByShopCode(shopkeeperWarehouse));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param shopkeeperWarehouseKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("shopkeeperWarehouseKey") String shopkeeperWarehouseKey) {
        return ResponseData.success(this.shopkeeperWarehouseService.queryById(shopkeeperWarehouseKey));
    }

    /**
     * 新增数据
     *
     * @param shopkeeperWarehouse 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody ShopkeeperWarehouse shopkeeperWarehouse) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        shopkeeperWarehouse.setShopkeeperWarehouseKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.shopkeeperWarehouseService.insert(shopkeeperWarehouse));
    }

    /**
     * 编辑数据
     *
     * @param shopkeeperWarehouse 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody ShopkeeperWarehouse shopkeeperWarehouse) {
        return ResponseData.success(this.shopkeeperWarehouseService.update(shopkeeperWarehouse));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody ShopkeeperWarehouse shopkeeperWarehouse) {
        return ResponseData.success(this.shopkeeperWarehouseService.deleteById(shopkeeperWarehouse));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<ShopkeeperWarehouse> shopkeeperWarehouseKeys) {
        for(ShopkeeperWarehouse shopkeeperWarehouse: shopkeeperWarehouseKeys){
            this.shopkeeperWarehouseService.deleteById(shopkeeperWarehouse);
        }
        return ResponseData.success();
    }
}

