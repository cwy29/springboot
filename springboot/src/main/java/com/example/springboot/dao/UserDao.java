package com.example.springboot.dao;

import com.example.springboot.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user where username=#{username}and userpwd=#{userpwd}")
    public User getAllUsers(@Param("username")String username,@Param("userpwd")String userpwd);

}
