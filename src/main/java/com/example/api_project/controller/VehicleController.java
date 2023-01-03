package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Vehicle;
import com.example.api_project.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * 车辆(Vehicle)表控制层
 *
 */
@RestController
@RequestMapping("api/vehicle")
public class VehicleController {
    /**
     * 服务对象
     */
    @Autowired
    private VehicleService vehicleService;

    /**
     * 分页查询
     *
     * @param vehicle 筛选条件
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Vehicle vehicle, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.vehicleService.queryByPage(vehicle, startRows,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param vehicleKey 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("vehicleKey") String vehicleKey) {
        return ResponseData.success(this.vehicleService.queryById(vehicleKey));
    }

    /**
     * 新增数据
     *
     * @param vehicle 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Vehicle vehicle) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        vehicle.setVehicleKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.vehicleService.insert(vehicle));
    }

    /**
     * 编辑数据
     *
     * @param vehicle 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Vehicle vehicle) {
        return ResponseData.success(this.vehicleService.update(vehicle));
    }

    /**
     * 删除数据
     *
     * @param vehicle 主键
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Vehicle vehicle) {
        if(this.vehicleService.deleteById(vehicle)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.vehicleService.deleteById(vehicle));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Vehicle> vehicleKeys) {
        for(Vehicle vehicle: vehicleKeys){
            this.vehicleService.deleteById(vehicle);
        }
        return ResponseData.success();
    }
}

