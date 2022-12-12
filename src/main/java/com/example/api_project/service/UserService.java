package com.example.api_project.service;

import com.example.api_project.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    /**
     * 分页查询 User 带条件
     * @param userName
     * @param userType
     * @param startRows
     * @param pageSize
     * @return
     */
    List<User> selectUserPage(@Param("userName")String userName, @Param("userType")String userType, @Param("startRows")Integer startRows,@Param("pageSize")Integer pageSize);

    /**
     * 查询 User 个数
     * @param userName
     * @param userType
     * @return
     */
    Integer getRowCount(@Param("userName")String userName, @Param("userType")String userType);

    /**
     * 添加 User
     * @param user
     * @return 返回码
     */
    Integer createUser(User user);

    /**
     * 根据 userId 删除用户
     * @param userId
     * @return 返回码
     */
    Integer deleteUserById(String userId);

    /**
     * 根据 userId 批量删除用户
     * @param userIds
     * @return
     */
    Integer deleteUserByIdList(List userIds);

    /**
     * 根据 userId 更新用户
     * @return 返回码
     */
    Integer updateUserById(User user);

    /**
     * 根据id查一个用户
     *
     * @param user
     * @return 返回码
     */
    User findOneUser(User user);

    /**
     * 根据用户id、密码查一个用户
     *
     * @param user
     * @return 返回码
     */
    User selectForLogin(User user);


    /**
     * 注册
     * @param user
     * @return 返回码
     */
    Integer register(User user);
}