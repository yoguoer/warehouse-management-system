package com.example.api_project.controller;

import com.example.api_project.pojo.DetailWarehouse;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.service.DetailWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 出入库明细表(DetailWarehouse)表控制层
 *
 */
@RestController
@RequestMapping("api/detailWarehouse")
public class DetailWarehouseController {
    /**
     * 服务对象
     */
    @Autowired
    private DetailWarehouseService detailWarehouseService;

    /**
     * 分页查询
     *
     * @param detailWarehouse 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(DetailWarehouse detailWarehouse, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.detailWarehouseService.queryByPage(detailWarehouse, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param detailWarehouseKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("detailWarehouseKey") String detailWarehouseKey) {
        return ResponseData.success(this.detailWarehouseService.queryById(detailWarehouseKey));
    }

    /**
     * 新增数据
     *
     * @param detailWarehouse 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody DetailWarehouse detailWarehouse) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        detailWarehouse.setDetailWarehouseKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.detailWarehouseService.insert(detailWarehouse));
    }

    /**
     * 编辑数据
     *
     * @param detailWarehouse 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody DetailWarehouse detailWarehouse) {
        return ResponseData.success(this.detailWarehouseService.update(detailWarehouse));
    }

    /**
     * 删除数据
     *
     * @param detailWarehouse
     * @return 删除是否成功
     */
    @PostMapping ("/delete")
    public Result deleteById(@RequestBody DetailWarehouse detailWarehouse) {
        return ResponseData.success(this.detailWarehouseService.deleteById(detailWarehouse));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<DetailWarehouse> detailWarehouseKeys) {
        for(DetailWarehouse detailWarehouse: detailWarehouseKeys){
            this.detailWarehouseService.deleteById(detailWarehouse);
        }
        return ResponseData.success();
    }
}

