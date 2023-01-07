package com.example.api_project.pojo;

public class User {
    private String userId;//用户id
    private String userName;//用户姓名
    private String userSex;//用户性别
    private String userEmail;//邮箱
    private String passWord;//密码
    private String userPhone;//手机号码
    private Integer userType;//用户类型(0超级管理员、1仓库管理员、2普通用户)
    private String userBelong;//归属于

    public String getUserBelong() {
        return userBelong;
    }

    public void setUserBelong(String userBelong) {
        this.userBelong = userBelong;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}