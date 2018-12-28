package com.example.springboot.controller;

import com.example.springboot.bean.User;
import com.example.springboot.redis.BasePrefix;
import com.example.springboot.redis.RedisService;
import com.example.springboot.redis.UserKey;
import com.example.springboot.result.Result;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/GetRedis")
    @ResponseBody
    public Result<User> redisGet(){
       User userID=redisService.get(UserKey.getById,""+1,User.class);
       return Result.success(userID);

    }

    @ResponseBody
    @RequestMapping("/SetRedis")
    public Result<User>SetRedis(){
        /*redisService.set("age",23);
        Integer age=redisService.get("age",Integer.class);
        return Result.success(age);*/
        User user=new User();
        user.setId(1);
        user.setUsername("沈国建");
        user.setUserpwd("123");
        redisService.set(UserKey.getById,""+1,user);
        user=redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);

    }

}
