package com.example.api_project.service;

import com.example.api_project.pojo.BusinessEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业务主体(BusinessEntity)表服务接口
 *
 */
public interface BusinessEntityService {

    /**
     * 通过ID查询单条数据
     *
     * @param entityCode 主键
     * @return 实例对象
     */
    BusinessEntity queryById(String entityCode);

    /**
     * 不分页查询
     */
    List<BusinessEntity> querylist();

    /**
     * 分页查询
     *
     * @param businessEntity 筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    Map<String, Object> queryByPage(BusinessEntity businessEntity, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param businessEntity 实例对象
     * @return 实例对象
     */
    BusinessEntity insert(BusinessEntity businessEntity);

    /**
     * 修改数据
     *
     * @param businessEntity 实例对象
     * @return 实例对象
     */
    BusinessEntity update(BusinessEntity businessEntity);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(BusinessEntity businessEntity);

}
