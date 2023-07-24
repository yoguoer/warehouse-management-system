package com.example.api_project.controller;

import com.example.api_project.pojo.TransferCheck;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.service.TransferCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 调货审批表(TransferCheck)表控制层
 *
 */
@RestController
@RequestMapping("api/transferCheck")
@CrossOrigin(origins = "*")

public class TransferCheckController {
    /**
     * 服务对象
     */
    @Autowired
    private TransferCheckService transferCheckService;

    /**
     * 分页查询
     *
     * @param transferCheck 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(TransferCheck transferCheck, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.transferCheckService.queryByPage(transferCheck,startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.transferCheckService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param transferCheck 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody TransferCheck transferCheck) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        transferCheck.setTransferCheckKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.transferCheckService.insert(transferCheck));
    }

    /**
     * 编辑数据
     *
     * @param transferCheck 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody TransferCheck transferCheck) {
        return ResponseData.success(this.transferCheckService.update(transferCheck));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody TransferCheck transferCheck) {
        return ResponseData.success(this.transferCheckService.deleteById(transferCheck));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<TransferCheck> transferCheckKeys) {
        for(TransferCheck transferCheck: transferCheckKeys){
            this.transferCheckService.deleteById(transferCheck);
        }
        return ResponseData.success();
    }
}

