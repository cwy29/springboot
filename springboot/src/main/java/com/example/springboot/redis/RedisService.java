package com.example.springboot.redis;

import java.util.ArrayList;
import java.util.List;

import com.example.springboot.bean.User;
import com.example.springboot.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.*;

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * 获取键key所对应的value值
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key,Class<T>clazz){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            //生成一个真正的key 给key加一个前缀标记
             String realKey=prefix.getPrefix()+key;
            //如何把一个字符串转化成Bean对象
            String str=jedis.get(realKey);
            T t=stringToBean(str,clazz);
            return t;
        }
        finally {
            returnToPool(jedis) ;
        }
    }


    /**
     *设置键key所对应的value值
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            //如何把一个Bean对象转化成字符串
            String str=beanToString(value);
            if (str==null||str.length()<=0)
            {
                return false;
            }
            //生成一个真正的key 给key加一个前缀标记
            String realKey=prefix.getPrefix()+key;
            //有效期
            int second=prefix.expireSeconds();
            //判断有效期是不是默认值0为永不过期
            if (second<=0)
            {
                jedis.set(realKey,str);//设置的key-value将永不过期
            }
            else {
                jedis.setex(realKey,second,str);//为key-value设置有效期
            }

            return true;
        }
        finally {
            returnToPool(jedis) ;
        }
    }



    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix,String key){

        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }

    }

    /**
     * 增值，key所对应的value值会自动加1
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */

    public <T> Long incr(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return  jedis.incr(realKey);
        }
        finally {
            returnToPool(jedis);
        }
    }
    /**
     * 减值，key所对应的value值会自动减1
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */

    public <T> Long decr(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return  jedis.decr(realKey);
        }
        finally {
            returnToPool(jedis);
        }
    }



    /**
     * 如何把一个字符串转化成Bean对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
@SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str==null||str.length()<=0||clazz==null)
        {
            return null;
        }
        //如果value是整形类型
        if (clazz==int.class||clazz==Integer.class){
            return (T)Integer.valueOf(str);

        }
        //如果value是长整形类型
        else if (clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(str);
        }
        //如果value是String类型
        else if (clazz==String.class)
        {
            return (T) str;
        }
        //如果value是其他类型
        else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
}




    /**
     * 如何把一个Bean对象转化成字符串
     * @param value
     * @return
     */

    private <T> String beanToString(T value) {

        if (value==null)
        {
            return null;
        }
        //判断value的类型
        Class<?>clazz=value.getClass();
        //如果value是整形类型
        if (clazz==int.class||clazz==Integer.class){
            return ""+value;

        }
        //如果value是长整形类型
        else if (clazz==long.class||clazz==Long.class){
            return ""+value;
        }
        //如果value是String类型
        else if (clazz==String.class)
        {
            return (String) value;
        }
        //如果value是其他类型
        else {
            return JSON.toJSONString(value);
        }
    }




    /**
     * 将jedis归还给jedisPool
     * @param jedis
     */

    private void returnToPool(Jedis jedis) {
        if (jedis!=null){
            jedis.close();//归还
        }
    }


}