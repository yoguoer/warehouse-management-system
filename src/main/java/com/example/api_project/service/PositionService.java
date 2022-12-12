package com.example.api_project.service;

import com.example.api_project.pojo.Position;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 货位(Position)表服务接口
 *
 */
public interface PositionService {

    /**
     * 通过ID查询单条数据
     *
     * @param positionKey 主键
     * @return 实例对象
     */
    Position queryById(String positionKey);

    /**
     * 分页查询
     *
     * @param position    筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Position position, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    Position insert(Position position);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    Position update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param position
     * @return 是否成功
     */
    boolean deleteById(Position position);

}
