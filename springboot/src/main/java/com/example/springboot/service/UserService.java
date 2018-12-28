package com.example.springboot.service;

import com.example.springboot.bean.User;
import com.example.springboot.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getAllUsers(String username, String userpwd) {

        User user = userDao.getAllUsers(username, userpwd);
        if (user.getUsername().equals(username) && user.getUserpwd().equals(userpwd)) {
            return user;
        }
        return null;
    }
}
