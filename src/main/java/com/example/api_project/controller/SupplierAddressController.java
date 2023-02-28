package com.example.api_project.controller;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.SupplierAddress;
import com.example.api_project.service.SupplierAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 供应商的地址表(SupplierAddress)表控制层
 *
 */
@RestController
@RequestMapping("api/supplierAddress")
@CrossOrigin(origins = "*")

public class SupplierAddressController {
    /**
     * 服务对象
     */
    @Autowired
    private SupplierAddressService supplierAddressService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.supplierAddressService.querylist());
    }


    /**
     * 分页查询
     *
     * @param supplierAddress 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(SupplierAddress supplierAddress, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.supplierAddressService.queryByPage(supplierAddress, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.supplierAddressService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param supplierAddress 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody SupplierAddress supplierAddress) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        supplierAddress.setAddressKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.supplierAddressService.insert(supplierAddress));
    }

    /**
     * 编辑数据
     *
     * @param supplierAddress 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody SupplierAddress supplierAddress) {
        return ResponseData.success(this.supplierAddressService.update(supplierAddress));
    }

    /**
     * 删除数据
     *
     * @param supplierAddress
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody SupplierAddress supplierAddress) {
        if(this.supplierAddressService.deleteById(supplierAddress)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.supplierAddressService.deleteById(supplierAddress));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<SupplierAddress> supplierAddressKeys) {
        for(SupplierAddress supplierAddress: supplierAddressKeys){
            this.supplierAddressService.deleteById(supplierAddress);
        }
        return ResponseData.success();
    }
}

