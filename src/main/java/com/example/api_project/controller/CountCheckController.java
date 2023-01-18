package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.CountCheck;
import com.example.api_project.service.CountCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 盘亏盘盈审批表(CountCheck)表控制层
 *
 */
@RestController
@RequestMapping("api/countCheck")
public class CountCheckController {
    /**
     * 服务对象
     */
    @Autowired
    private CountCheckService countCheckService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.countCheckService.querylist());
    }

    /**
     * 分页查询
     *
     * @param countCheck 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(CountCheck countCheck, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.countCheckService.queryByPage(countCheck,startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param countCheckKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(String countCheckKey) {
        return ResponseData.success(this.countCheckService.queryById(countCheckKey));
    }

    /**
     * 新增数据
     *
     * @param countCheck 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody CountCheck countCheck) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        countCheck.setCountCheckKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.countCheckService.insert(countCheck));
    }

    /**
     * 编辑数据
     *
     * @param countCheck 实体
     * @return 编辑结果
     */
    @PutMapping("/update")
    public Result edit(@RequestBody CountCheck countCheck) {
        return ResponseData.success(this.countCheckService.update(countCheck));
    }

    /**
     * 删除数据
     *
     * @param countCheck
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public Result deleteById(@RequestBody CountCheck countCheck) {
        return ResponseData.success(this.countCheckService.deleteById(countCheck));
    }

}

