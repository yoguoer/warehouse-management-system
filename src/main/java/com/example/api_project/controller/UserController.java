package com.example.api_project.controller;

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

import java.util.*;

@Controller
@RequestMapping("api/user")
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/selectUserPage")
    @ResponseBody
    public Result selectUserPage(String userName, String userType, Integer page,Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        List records = userService.selectUserPage(userName, userType, startRows,pageSize);
        Integer total = userService.getRowCount(userName, userType);
        Map<String,Object> res = new HashMap<>();
        res.put("records",records);
        res.put("total",total);
        return ResponseData.success(res);
    }

    @RequestMapping("/createUser")
    @ResponseBody
    public Result createUser(@RequestBody User user) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        user.setUserId(System.currentTimeMillis() + String.valueOf(number));
        Integer i = userService.createUser(user);
        return ResponseData.success(i);
    }

    @RequestMapping("/deleteUserById")
    @ResponseBody
    public Result deleteUserById(String userId) {
        Integer i = userService.deleteUserById(userId);
        return ResponseData.success(i);
    }

    @RequestMapping(value = "/deleteUserByIdList")
    @ResponseBody
    public Result deleteUserByIdList(String userIdList) {
        String userIdListSub = userIdList.substring(0, userIdList.length() - 1);
        //        String[] userIds = userIdList.split(",");
        List userIds = new ArrayList();
        for (String userIdStr : userIdListSub.split(",")) {
            userIds.add(userIdStr.trim());
        }
        Integer i = userService.deleteUserByIdList(userIds);
        return ResponseData.success(i);
    }

    @RequestMapping("/updateUserById")
    @ResponseBody
    public Result updateUserById(@RequestBody User user) {
        Integer i = userService.updateUserById(user);
        return ResponseData.success(i);
    }
}