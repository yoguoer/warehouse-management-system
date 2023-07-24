package com.example.api_project.controller;

import com.example.api_project.pojo.Brand;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 品牌(Brand)表控制层
 */
@RestController
@RequestMapping("api/brand")
@CrossOrigin(origins = "*")

public class BrandController {
    /**
     * 服务对象
     */
    @Autowired
    private BrandService brandService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.brandService.querylist());
    }

    /**
     * 分页查询
     *
     * @param brand 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Brand brand, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.brandService.queryByPage(brand, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param brandKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("brandKey") String brandKey) {
        return ResponseData.success(this.brandService.queryById(brandKey));
    }

    /**
     * 新增数据
     *
     * @param brand 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Brand brand) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        brand.setBrandKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.brandService.insert(brand));
    }

    /**
     * 编辑数据
     *
     * @param brand 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Brand brand) {
        return ResponseData.success(this.brandService.update(brand));
    }

    /**
     * 删除数据
     *
     * @param brand 主键
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody Brand brand) {
        if(this.brandService.deleteById(brand)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Brand> brandKeys) {
        for(Brand brand: brandKeys){
            this.brandService.deleteById(brand);
        }
        return ResponseData.success();
    }
}

