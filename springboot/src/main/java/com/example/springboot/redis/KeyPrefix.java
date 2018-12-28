package com.example.springboot.redis;

/**
 * 缓存key的接口
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}