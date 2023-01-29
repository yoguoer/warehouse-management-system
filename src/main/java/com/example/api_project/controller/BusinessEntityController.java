package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.BusinessEntity;
import com.example.api_project.service.BusinessEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务主体(BusinessEntity)表控制层
 *
 */
@RestController
@RequestMapping("api/entity")
public class BusinessEntityController {
    /**
     * 服务对象
     */
    @Autowired
    private BusinessEntityService businessEntityService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.businessEntityService.querylist());
    }

    /**
     * 分页查询
     *
     * @param businessEntity 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(BusinessEntity businessEntity, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.businessEntityService.queryByPage(businessEntity,startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param entityCode 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("entityCode") String entityCode) {
        return ResponseData.success(this.businessEntityService.queryById(entityCode));
    }

    /**
     * 新增数据
     *
     * @param businessEntity 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody BusinessEntity businessEntity) {
        return ResponseData.success(this.businessEntityService.insert(businessEntity));
    }

    /**
     * 编辑数据
     *
     * @param businessEntity 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody BusinessEntity businessEntity) {
        return ResponseData.success(this.businessEntityService.update(businessEntity));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody BusinessEntity businessEntity) {
        return ResponseData.success(this.businessEntityService.deleteById(businessEntity));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<BusinessEntity> businessEntityCodes) {
        for(BusinessEntity businessEntity: businessEntityCodes){
            this.businessEntityService.deleteById(businessEntity);
        }
        return ResponseData.success();
    }
}

