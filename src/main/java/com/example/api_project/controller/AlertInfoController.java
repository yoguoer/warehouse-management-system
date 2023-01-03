package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.AlertInfo;
import com.example.api_project.service.AlertInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 预警信息表(AlertInfo)表控制层
 *
 */
@RestController
@RequestMapping("api/alertInfo")
public class AlertInfoController {
    /**
     * 服务对象
     */
    @Autowired
    private AlertInfoService alertInfoService;

    /**
     * 分页查询
     *
     * @param alertInfo 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(AlertInfo alertInfo, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.alertInfoService.queryByPage(alertInfo, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.alertInfoService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param alertInfo 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody AlertInfo alertInfo) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        alertInfo.setAlertInfoKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.alertInfoService.insert(alertInfo));
    }

    /**
     * 编辑数据
     *
     * @param alertInfo 实体
     * @return 编辑结果
     */
    @PutMapping("/update")
    public Result edit(@RequestBody AlertInfo alertInfo) {
        return ResponseData.success(this.alertInfoService.update(alertInfo));
    }

    /**
     * 删除数据
     *
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public Result deleteById(@RequestBody AlertInfo AlertInfo) {
        return ResponseData.success(this.alertInfoService.deleteById(AlertInfo));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<AlertInfo> AlertInfoKeys) {
        for(AlertInfo AlertInfo: AlertInfoKeys){
            this.alertInfoService.deleteById(AlertInfo);
        }
        return ResponseData.success();
    }
}

