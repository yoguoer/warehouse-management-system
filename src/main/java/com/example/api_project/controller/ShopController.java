package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Shop;
import com.example.api_project.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 门店表(Shop)表控制层
 *
 */
@RestController
@RequestMapping("api/shop")
public class ShopController {
    /**
     * 服务对象
     */
    @Autowired
    private ShopService shopService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.shopService.querylist());
    }
    /**
     * 分页查询
     *
     * @param shop 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Shop shop, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.shopService.queryByPage(shop, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param shopKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("shopKey") String shopKey) {
        return ResponseData.success(this.shopService.queryById(shopKey));
    }

    /**
     * 新增数据
     *
     * @param shop 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Shop shop) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        shop.setShopKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.shopService.insert(shop));
    }

    /**
     * 编辑数据
     *
     * @param shop 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Shop shop) {
        return ResponseData.success(this.shopService.update(shop));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody Shop shop) {
        return ResponseData.success(this.shopService.deleteById(shop));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Shop> shopKeys) {
        for(Shop shop: shopKeys){
            this.shopService.deleteById(shop);
        }
        return ResponseData.success();
    }
}

