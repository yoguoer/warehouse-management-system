package com.example.api_project.service;


import com.example.api_project.pojo.Vehicle;

import java.util.List;
import java.util.Map;

/**
 * 车辆(Vehicle)表服务接口
 *
 */
public interface VehicleService {

    /**
     * 通过ID查询单条数据
     *
     * @param vehicleKey 主键
     * @return 实例对象
     */
    Vehicle queryById(String vehicleKey);

    /**
     * 分页查询
     *
     * @param vehicle     筛选条件
     * @return 查询结果
     */
    Map<String,Object> queryByPage(Vehicle vehicle, Integer startRows, Integer pageSize);

    /**
     * 新增数据
     *
     * @param vehicle 实例对象
     * @return 实例对象
     */
    Vehicle insert(Vehicle vehicle);

    /**
     * 修改数据
     *
     * @param vehicle 实例对象
     * @return 实例对象
     */
    Vehicle update(Vehicle vehicle);

    /**
     * 通过主键删除数据
     *
     * @param vehicle
     * @return 是否成功
     */
    boolean deleteById(Vehicle vehicle);

}
