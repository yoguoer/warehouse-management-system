package com.example.api_project.service;

import com.example.api_project.pojo.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门店表(Shop)表服务接口
 *
 */
public interface ShopService {

    /**
     * 通过ID查询单条数据
     *
     * @param shopKey 主键
     * @return 实例对象
     */
    Shop queryById(String shopKey);

    /**
     * 通过ID查询单条数据
     *
     * @return 实例对象
     */
    Shop queryByshopCode(Shop shop);

    /**
     * 不分页查询
     */
    List<Shop> querylist();

    /**
     * 分页查询
     *
     * @param shop 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Shop shop, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    Shop insert(Shop shop);

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    Shop update(Shop shop);

    /**
     * 通过主键删除数据
     *
     * @return 是否成功
     */
    boolean deleteById(Shop shop);

}
