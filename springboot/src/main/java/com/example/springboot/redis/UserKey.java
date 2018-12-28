package com.example.springboot.redis;

public class UserKey extends BasePrefix {

    //希望缓存在redis中的用户对象信息用不过期
    public UserKey(String prefix) {
        super(prefix);//父类的有效期默认为0，prefix永不过期
    }

   //不希望外界实例化UserKey
    public static UserKey getById=new UserKey("id_");
    public static UserKey getByName=new UserKey("name_");
}
