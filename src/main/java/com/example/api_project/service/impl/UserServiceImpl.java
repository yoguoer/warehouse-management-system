package com.example.api_project.service.impl;

import com.example.api_project.mapper.UserMapper;
import com.example.api_project.pojo.User;
import com.example.api_project.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getList(String userType){
        return userMapper.getList(userType);
    }

    @Override
    public List<User> selectUserPage(String userName, String userType,String userBelong, Integer startRows,Integer pageSize) {
        return userMapper.selectUserPage(userName, userType, userBelong, startRows, pageSize);
    }

    @Override
    public Integer getRowCount(String userName, String userType, String userBelong) {
        return userMapper.getRowCount(userName, userType,userBelong);
    }

    @Override
    public Integer createUser(User user) {
        Integer i = userMapper.insert(user);
        return i;
    }

    @Override
    public Integer deleteUserById(String userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public Integer deleteUserByIdList(List userIds) {
        return userMapper.deleteUserByIdList(userIds);
    }

    @Override
    public Integer updateUserById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findOneUser(User user) {
        String userPhone = user.getUserPhone();
        return userMapper.selectByPrimaryKey(userPhone);
    }

    @Override
    public User selectForLogin(User user) {
        return userMapper.selectForLogin(user);
    }

    @Override
    public Integer register(User user) {
        return userMapper.insert(user);
    }

}