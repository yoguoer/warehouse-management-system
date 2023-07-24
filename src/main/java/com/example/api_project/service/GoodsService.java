package com.example.api_project.service;

import com.example.api_project.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品表(Goods)表服务接口
 *
 */
public interface GoodsService {

    boolean setNoCategory(String categoryKey);

    /**
     * 不分页查询
     */
    List<Goods> querylist();

    /**
     * 通过ID查询单条数据
     *
     * @param goodsKey 主键
     * @return 实例对象
     */
    Goods queryById(String goodsKey);

    /**
     * 分页查询
     *
     * @param goods 筛选条件
     * @return 查询结果
     */
    Map<String, Object> queryByPage(Goods goods, @Param("startRows")Integer startRows, @Param("pageSize")Integer pageSize);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Goods insert(Goods goods);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Goods update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param goods 实例对象
     * @return 是否成功
     */
    boolean deleteById(Goods goods);

}
