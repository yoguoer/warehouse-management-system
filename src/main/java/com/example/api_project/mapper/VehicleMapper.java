package com.example.api_project.mapper;
import com.example.api_project.pojo.Vehicle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆(Vehicle)表数据库访问层
 *
 */
public interface VehicleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param vehicleKey 主键
     * @return 实例对象
     */
    Vehicle queryById(String vehicleKey);

    /**
     * 查询指全部数据
     *
     * @param vehicle    查询条件
     * @return 对象列表
     */
    List<Vehicle> getListPage(Vehicle vehicle, Integer startRows, Integer pageSize);

    /**
     * 查询指定行数据
     *
     *  分页对象
     * @return 对象列表
     */
    List<Vehicle> queryAllByLimit(String vehicleType,String vehicleCode, Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param vehicle 查询条件
     * @return 总行数
     */
    long count(Vehicle vehicle);

    /**
     * 新增数据
     *
     * @param vehicle 实例对象
     * @return 影响行数
     */
    int insert(Vehicle vehicle);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Vehicle> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Vehicle> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Vehicle> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Vehicle> entities);

    /**
     * 修改数据
     *
     * @param vehicle 实例对象
     * @return 影响行数
     */
    int update(Vehicle vehicle);

    /**
     * 通过主键删除数据
     *
     * @param vehicle
     * @return 影响行数
     */
    int deleteById(Vehicle vehicle);

}

