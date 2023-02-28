package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.SupplierBilling;
import com.example.api_project.service.SupplierBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 供应商的开票信息对应表(SupplierBilling)表控制层
 *
 */
@RestController
@RequestMapping("api/supplierBilling")
@CrossOrigin(origins = "*")

public class SupplierBillingController {
    /**
     * 服务对象
     */
    @Autowired
    private SupplierBillingService supplierBillingService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.supplierBillingService.querylist());
    }

    /**
     * 分页查询
     *
     * @param supplierBilling 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(SupplierBilling supplierBilling, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.supplierBillingService.queryByPage(supplierBilling, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.supplierBillingService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param supplierBilling 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody SupplierBilling supplierBilling) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        supplierBilling.setBillingKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.supplierBillingService.insert(supplierBilling));
    }

    /**
     * 编辑数据
     *
     * @param supplierBilling 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody SupplierBilling supplierBilling) {
        return ResponseData.success(this.supplierBillingService.update(supplierBilling));
    }

    /**
     * 删除数据
     *
     * @param supplierBilling
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody SupplierBilling supplierBilling) {
        if(this.supplierBillingService.deleteById(supplierBilling)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.supplierBillingService.deleteById(supplierBilling));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<SupplierBilling> supplierBillingKeys) {
        for(SupplierBilling supplierBilling: supplierBillingKeys){
            this.supplierBillingService.deleteById(supplierBilling);
        }
        return ResponseData.success();
    }
}

