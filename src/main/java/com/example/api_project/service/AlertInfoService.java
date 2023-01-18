package com.example.api_project.service;

import com.example.api_project.pojo.AlertInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 预警信息表(AlertInfo)表服务接口
 *
 */
public interface AlertInfoService {
    /**
     * 通过预警对象、商品、类型、状态来判断是否存在过
     *
     * @param alertInfo
     * @return 单条数据
     */
    boolean queryByMany(AlertInfo alertInfo);

    /**
     * 通过ID查询单条数据
     *
     * @param alertInfoKey 主键
     * @return 实例对象
     */
    AlertInfo queryById(String alertInfoKey);

    /**
     * 分页查询
     *
     * @param alertInfo 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(AlertInfo alertInfo, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param alertInfo 实例对象
     * @return 实例对象
     */
    AlertInfo insert(AlertInfo alertInfo);

    /**
     * 修改数据
     *
     * @param alertInfo 实例对象
     * @return 实例对象
     */
    AlertInfo update(AlertInfo alertInfo);

    /**
     * 通过主键删除数据
     *
     * @param alertInfo
     * @return 是否成功
     */
    boolean deleteById(AlertInfo alertInfo);

}
