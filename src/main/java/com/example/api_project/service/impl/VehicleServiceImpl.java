package com.example.api_project.service.impl;

import com.example.api_project.mapper.VehicleMapper;
import com.example.api_project.pojo.Brand;
import com.example.api_project.pojo.Vehicle;
import com.example.api_project.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆(Vehicle)表服务实现类
 *
 */
@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleMapper vehicleMapper;

    /**
     * 不分页查询
     */
    public List<Vehicle> querylist() {
        return vehicleMapper.getList();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param vehicleKey 主键
     * @return 实例对象
     */
    @Override
    public Vehicle queryById(String vehicleKey) {
        return this.vehicleMapper.queryById(vehicleKey);
    }

    /**
     * 分页查询
     *
     * @param vehicle     筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String,Object> queryByPage(Vehicle vehicle, Integer startRows, Integer pageSize) {
        long total = this.vehicleMapper.count(vehicle);
        String vehicleType=vehicle.getVehicleType();
        String vehicleCode=vehicle.getVehicleCode();
        List<Vehicle> records = this.vehicleMapper.queryAllByLimit(vehicleType,vehicleCode, startRows,pageSize);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param vehicle 实例对象
     * @return 实例对象
     */
    @Override
    public Vehicle insert(Vehicle vehicle) {
        this.vehicleMapper.insert(vehicle);
        return vehicle;
    }

    /**
     * 修改数据
     *
     * @param vehicle 实例对象
     * @return 实例对象
     */
    @Override
    public Vehicle update(Vehicle vehicle) {
        this.vehicleMapper.update(vehicle);
        return this.queryById(vehicle.getVehicleKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param vehicle
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Vehicle vehicle) {
        return this.vehicleMapper.deleteById(vehicle) > 0;
    }
}
