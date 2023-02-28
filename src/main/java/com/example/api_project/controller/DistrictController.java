package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.District;
import com.example.api_project.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 区域(District)表控制层
 *
 */
@RestController
@RequestMapping("api/district")
@CrossOrigin(origins = "*")

public class DistrictController {
    /**
     * 服务对象
     */
    @Autowired
    private DistrictService districtService;

    @GetMapping("/list")
    public Result querylist(District district) {
        return ResponseData.success(this.districtService.querylist(district));
    }

    /**
     * 分页查询
     *
     * @param district 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(District district, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.districtService.queryByPage(district, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.districtService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param district 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody District district) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        district.setInventoryDistrictkey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.districtService.insert(district));
    }

    /**
     * 编辑数据
     *
     * @param district 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody District district) {
        return ResponseData.success(this.districtService.update(district));
    }

    /**
     * 删除数据
     *
     * @param district
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody District district) {
        if(this.districtService.deleteById(district)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.districtService.deleteById(district));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<District> inventoryDistrictkeys) {
        for(District district: inventoryDistrictkeys){
            this.districtService.deleteById(district);
        }
        return ResponseData.success();
    }
}

