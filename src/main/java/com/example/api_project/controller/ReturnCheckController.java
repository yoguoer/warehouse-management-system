package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.ReturnCheck;
import com.example.api_project.service.ReturnCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 退货审批表(ReturnCheck)表控制层
 *
 */
@RestController
@RequestMapping("api/returnCheck")
public class ReturnCheckController {
    /**
     * 服务对象
     */
    @Autowired
    private ReturnCheckService returnCheckService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.returnCheckService.querylist());
    }

    /**
     * 分页查询
     *
     * @param returnCheck 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(ReturnCheck returnCheck, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.returnCheckService.queryByPage(returnCheck, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("returnCheckKey") String returnCheckKey) {
        return ResponseData.success(this.returnCheckService.queryById(returnCheckKey));
    }

    /**
     * 新增数据
     *
     * @param returnCheck 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody ReturnCheck returnCheck) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        returnCheck.setReturnCheckKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.returnCheckService.insert(returnCheck));
    }

    /**
     * 编辑数据
     *
     * @param returnCheck 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody ReturnCheck returnCheck) {
        return ResponseData.success(this.returnCheckService.update(returnCheck));
    }

    /**
     * 删除数据
     *
     * @param returnCheck
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody ReturnCheck returnCheck) {
        return ResponseData.success(this.returnCheckService.deleteById(returnCheck));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<ReturnCheck> returnCheckKeys) {
        for(ReturnCheck returnCheck: returnCheckKeys){
            this.returnCheckService.deleteById(returnCheck);
        }
        return ResponseData.success();
    }
}

