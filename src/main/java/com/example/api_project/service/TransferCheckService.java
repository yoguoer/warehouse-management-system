package com.example.api_project.service;

import com.example.api_project.pojo.TransferCheck;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 调货审批表(TransferCheck)表服务接口
 *
 */
public interface TransferCheckService {

    /**
     * 通过ID查询单条数据
     *
     * @param transferCheckKey 主键
     * @return 实例对象
     */
    TransferCheck queryById(String transferCheckKey);

    /**
     * 分页查询
     *
     * @param transferCheck 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(TransferCheck transferCheck, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param transferCheck 实例对象
     * @return 实例对象
     */
    TransferCheck insert(TransferCheck transferCheck);

    /**
     * 修改数据
     *
     * @param transferCheck 实例对象
     * @return 实例对象
     */
    TransferCheck update(TransferCheck transferCheck);

    /**
     * 通过主键删除数据
     *
     * @param transferCheck
     * @return 是否成功
     */
    boolean deleteById(TransferCheck transferCheck);

}
