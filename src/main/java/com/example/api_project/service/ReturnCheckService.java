package com.example.api_project.service;

import com.example.api_project.pojo.ReturnCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 退货审批表(ReturnCheck)表服务接口
 *
 */
public interface ReturnCheckService {

    /**
     * 不分页查询
     */
    List<ReturnCheck> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    ReturnCheck queryById(ReturnCheck returnCheck);

    /**
     * 分页查询
     *
     * @param returnCheck 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(ReturnCheck returnCheck, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param returnCheck 实例对象
     * @return 实例对象
     */
    ReturnCheck insert(ReturnCheck returnCheck);

    /**
     * 修改数据
     *
     * @param returnCheck 实例对象
     * @return 实例对象
     */
    ReturnCheck update(ReturnCheck returnCheck);

    /**
     * 通过主键删除数据
     *
     * @param  returnCheck
     * @return 是否成功
     */
    boolean deleteById( ReturnCheck returnCheck);

}
