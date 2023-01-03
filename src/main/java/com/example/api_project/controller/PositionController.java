package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Position;
import com.example.api_project.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;


/**
 * 货位(Position)表控制层
 *
 */
@RestController
@RequestMapping("api/position")
public class PositionController {
    /**
     * 服务对象
     */
    @Autowired
    private PositionService positionService;

    /**
     * 不分页查询
     */
    @GetMapping("/list")
    public Result querylist(Position position) {
        return ResponseData.success(this.positionService.querylist(position));
    }

    /**
     * 分页查询
     *
     * @param position 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Position position, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.positionService.queryByPage(position, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param positionKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("positionKey") String positionKey) {
        return ResponseData.success(this.positionService.queryById(positionKey));
    }

    /**
     * 新增数据
     *
     * @param position 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Position position) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        position.setPositionKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.positionService.insert(position));
    }

    /**
     * 编辑数据
     *
     * @param position 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Position position) {
        return ResponseData.success(this.positionService.update(position));
    }

    /**
     * 删除数据
     *
     * @param position 主键
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Position position) {
        if(this.positionService.deleteById(position)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.positionService.deleteById(position));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Position> positionKeys) {
        for(Position position: positionKeys){
            this.positionService.deleteById(position);
        }
        return ResponseData.success();
    }
}

