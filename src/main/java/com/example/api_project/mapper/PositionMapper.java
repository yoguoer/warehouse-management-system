package com.example.api_project.mapper;

import com.example.api_project.pojo.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 货位(Position)表数据库访问层
 *
 */
public interface PositionMapper {

    /**
     * 查询指全部数据
     * 不分页
     * @return 对象列表
     */
    List<Position> getList(String inventoryKey);

    /**
     * 通过ID查询单条数据
     *
     * @param positionKey 主键
     * @return 实例对象
     */
    Position queryById(String positionKey);

    /**
     * 查询指全部数据
     *
     * @param position    查询条件
     * @return 对象列表
     */
    List<Position> getListPage(Position position, Integer startRows, Integer pageSize);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Position> queryAllByLimit(String inventoryDistrictkey, String inventoryKey, String positionCode, String positionType,Integer startRows, Integer pageSize);

    /**
     * 统计总行数
     *
     * @param position 查询条件
     * @return 总行数
     */
    long count(Position position);

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int insert(Position position);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Position> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Position> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Position> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Position> entities);

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 影响行数
     */
    int update(Position position);

    /**
     * 通过主键删除数据
     *
     * @param position
     * @return 影响行数
     */
    int deleteById(Position position);

}

