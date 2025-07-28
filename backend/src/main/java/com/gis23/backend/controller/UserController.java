package com.gis23.backend.controller;

import com.gis23.backend.entity.User;
import com.gis23.backend.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("web")
@ResponseBody
@CrossOrigin(origins = "*")
@Api("用户登录")
public class UserController {
    @Autowired UserService userService;
    @ApiOperation("通过读取本地文件，将用户数据存储到数据库中")
    @RequestMapping("LoadUserData")
    String LoadUserData(@RequestParam(value = "file_path") String file_path){
        try {
            userService.LoadUserData(file_path);
            return "success";
        }catch (IOException e){
            return "文件读写报错";
        }

    }
    @RequestMapping("userLogin")
    public String userLogin(@RequestParam(value = "username") String username,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "user_role") String user_role){
        User user = userService.userLogin(username, password,user_role);
        if(user!=null){
            return "success";
        }else {
            return "error";
        }
    }
    @RequestMapping("userInfo")
    public User userInfo(@RequestParam(value = "username") String username,
                           @RequestParam(value = "user_role") String user_role){
        User user = userService.userInfo(username, user_role);
        return user;

    }
    @RequestMapping("updatePasswordById")
    public String updatePasswordById(@RequestParam(value = "user_id") int user_id,
                                 @RequestParam(value = "old_password") String old_password,
                                 @RequestParam(value = "new_password") String new_password){

        userService.updatePasswordById(user_id,old_password,new_password);
        return "success"; 

    }

}
