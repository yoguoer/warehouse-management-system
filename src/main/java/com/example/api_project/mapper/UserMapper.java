package com.example.api_project.mapper;

import com.example.api_project.pojo.User;

import java.util.List;


public interface UserMapper {

    List<User> getList(String userType);

    int deleteByPrimaryKey(String userId);

    int insert(User user);

    User selectByPrimaryKey(String userPhone);

    int updateByPrimaryKeySelective(User user);

    List<User> selectUserPage(String userName, String userType, String userBelong,Integer startRows, Integer pageSize);

    Integer getRowCount(String userName, String userType, String userBelong);

    Integer deleteUserByIdList(List userIds);

    User selectForLogin(User user);
}