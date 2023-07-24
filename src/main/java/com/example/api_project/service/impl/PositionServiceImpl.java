package com.example.api_project.service.impl;

import com.example.api_project.mapper.PositionMapper;
import com.example.api_project.pojo.Position;
import com.example.api_project.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货位(Position)表服务实现类
 *
 */
@Service("positionService")
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper positionMapper;

    /**
     * 不分页查询
     */
    public List<Position> querylist(Position position) {
        String inventoryKey=position.getInventoryKey();
        return positionMapper.getList(inventoryKey);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param positionKey 主键
     * @return 实例对象
     */
    @Override
    public Position queryById(String positionKey) {
        return this.positionMapper.queryById(positionKey);
    }

    /**
     * 分页查询
     *
     * @param position    筛选条件
     * @param startRows
     * @param pageSize
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Position position, Integer startRows, Integer pageSize) {
        String inventoryDistrictkey=position.getInventoryDistrictkey();
        String inventoryKey=position.getInventoryKey();
        String positionCode=position.getPositionCode();
        String positionType=position.getPositionType();
        List<Position> records = this.positionMapper.queryAllByLimit(inventoryDistrictkey,inventoryKey,positionCode,positionType,startRows, pageSize);
        long total = this.positionMapper.count(position);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    @Override
    public Position insert(Position position) {
        this.positionMapper.insert(position);
        return position;
    }

    /**
     * 修改数据
     *
     * @param position 实例对象
     * @return 实例对象
     */
    @Override
    public Position update(Position position) {
        this.positionMapper.update(position);
        return this.queryById(position.getPositionKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param position 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Position position) {
        return this.positionMapper.deleteById(position) > 0;
    }
}
