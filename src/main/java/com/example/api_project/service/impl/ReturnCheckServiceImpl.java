package com.example.api_project.service.impl;

import com.example.api_project.pojo.ReturnCheck;
import com.example.api_project.mapper.ReturnCheckMapper;
import com.example.api_project.service.ReturnCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退货审批表(ReturnCheck)表服务实现类
 *
 */
@Service("returnCheckService")
public class ReturnCheckServiceImpl implements ReturnCheckService {
    @Autowired
    private ReturnCheckMapper returnCheckMapper;

    /**
     * 不分页查询
     */
    public List<ReturnCheck> querylist() {
        return returnCheckMapper.getList();
    }
    /**
     * 通过ID查询单条数据
     *
     * @param  returnCheckKey
     * @return 实例对象
     */
    @Override
    public ReturnCheck queryById(String returnCheckKey ) {
        return this.returnCheckMapper.queryById(returnCheckKey);
    }

    /**
     * 分页查询
     *
     * @param returnCheck 筛选条件
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(ReturnCheck returnCheck, Integer startRows, Integer pageSize) {
        long total = this.returnCheckMapper.count(returnCheck);
        Integer checkStatus=returnCheck.getCheckStatus();
        Integer checkType=returnCheck.getCheckType();
        List<ReturnCheck> records;
        if(checkType==0){
            records = returnCheckMapper.queryAllByLimitInput(checkStatus,startRows, pageSize);
        }else{
            records = returnCheckMapper.queryAllByLimitOutput(checkStatus,startRows, pageSize);
        }
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return res;
    }

    /**
     * 新增数据
     *
     * @param returnCheck 实例对象
     * @return 实例对象
     */
    @Override
    public ReturnCheck insert(ReturnCheck returnCheck) {
        this.returnCheckMapper.insert(returnCheck);
        return returnCheck;
    }

    /**
     * 修改数据
     *
     * @param returnCheck 实例对象
     * @return 实例对象
     */
    @Override
    public ReturnCheck update(ReturnCheck returnCheck) {
        this.returnCheckMapper.update(returnCheck);
        return this.queryById(returnCheck.getReturnCheckKey());
    }

    /**
     * 通过主键删除数据
     *
     * @param  returnCheck
     * @return 是否成功
     */
    @Override
    public boolean deleteById( ReturnCheck returnCheck) {
        return this.returnCheckMapper.deleteById(returnCheck) > 0;
    }
}
