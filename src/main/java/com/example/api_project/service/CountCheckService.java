package com.example.api_project.service;

import com.example.api_project.pojo.CountCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 盘亏盘盈审批表(CountCheck)表服务接口
 *
 */
public interface CountCheckService {

    /**
     * 不分页查询
     */
    List<CountCheck> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @param  countCheckKey
     * @return 实例对象
     */
    CountCheck queryById(String countCheckKey );

    /**
     * 分页查询
     *
     * @param countCheck 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(CountCheck countCheck, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param countCheck 实例对象
     * @return 实例对象
     */
    CountCheck insert(CountCheck countCheck);

    /**
     * 修改数据
     *
     * @param countCheck 实例对象
     * @return 实例对象
     */
    CountCheck update(CountCheck countCheck);

    /**
     * 通过主键删除数据
     *
     * @param  countCheck
     * @return 是否成功
     */
    boolean deleteById(CountCheck countCheck);

}
