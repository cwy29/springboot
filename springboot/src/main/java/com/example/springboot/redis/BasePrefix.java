package com.example.springboot.redis;

/**
 * 实现缓存key的接口
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds ;//过期时间
    private String prefix ;//前缀标记

    //自己的构造方法
    public BasePrefix(String prefix) {
        this(0,prefix);//0代表永不过期
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }
    //实现接口的方法
    public int expireSeconds(){
        return expireSeconds;//0代表永不过期
    }
    public  String getPrefix(){
        //获得一个类的名称
       String className=getClass().getSimpleName();
       //返回一个指定格式的字符串     类名:前缀
       return className+":"+prefix;
    }



}