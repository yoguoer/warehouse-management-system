package com.example.api_project.controller;

import com.example.api_project.utils.JWT.JWT;
import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.User;
import com.example.api_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

import static com.example.api_project.model.ResponseData.error;

@Controller
@RequestMapping("api/login")
@CrossOrigin(origins = "*")

public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
//    @ResponseBody
    public String index() {
        //存在，返回该用户的信息
        return "index";
    }

    //登录
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user) {
        User temp= userService.selectForLogin(user);
        if(temp!=null){
            User u = userService.findOneUser(temp);
            //给用户jwt加密生成token【//创建JWT令牌】
            String token = JWT.sign(u, 60L* 1000L* 600L);
            Result responseData = new Result<>();//封装成对象返回给客户端
            responseData.putDataValue("userId", user.getUserId());
            responseData.putDataValue("token", token);
            responseData.putDataValue("user", u);
            responseData.Result();
            return responseData;
        }else if(userService.findOneUser(user)==null){
            return ResponseData.error("账号不存在，请重新输入！");
        }else{
            return ResponseData.error("账号或密码错误！");
        }
    }

    //注册
    @RequestMapping("/register")
    @ResponseBody
    public Result register(@RequestBody User user) {
        if(userService.findOneUser(user)==null){
            System.out.println(userService.findbyCode(user));
            if(userService.findbyCode(user)==null){
                Random random = new Random();
                Integer number = random.nextInt(9000) + 1000;
                user.setUserId(System.currentTimeMillis() + String.valueOf(number));
                Integer i = userService.createUser(user);
                if(i==1){
                    User u= userService.findOneUser(user);
                    return ResponseData.success(u);
                }else{
                    return ResponseData.error();
                }
            }else{
                return ResponseData.error("该账号已存在！");
            }
        }else{
            return ResponseData.error("该账号已存在！");
        }
    }

    //获取用户信息
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Result getUserInfo(@RequestBody User user) {
        User u = userService.findOneUser(user);
        return ResponseData.success(u);
    }

    @RequestMapping("/test")
    @ResponseBody
    public Result test(User user) {
        return ResponseData.success(user);
    }


}