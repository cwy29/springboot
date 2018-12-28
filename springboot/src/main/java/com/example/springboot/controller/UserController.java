package com.example.springboot.controller;

import com.example.springboot.bean.User;
import com.example.springboot.service.GoodsService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/demo")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/welcome")
    public String welcome(){

        return "login";
    }


}
