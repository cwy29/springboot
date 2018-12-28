package com.example.springboot.dao;

import com.example.springboot.bean.MiaoshaGoods;
import com.example.springboot.bean.MiaoshaOrder;
import com.example.springboot.bean.OrderInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {


    /**
     * 查询参与秒杀用户的ID和秒杀商品的ID
     *
     * @param userId
     * @param goodsId
     * @return
     */

    @Select("select * from miaoshaorder where userId=#{userId} and goodsId=#{goodsId}")
    public MiaoshaOrder getMiaoshaUseridAndgoodsid(@Param("userId")int userId,@Param("goodsId") Integer goodsId);


    /**
     * 插入订单信息
     *
     * @param orderInfo
     * @return
     */
    @Insert("insert into orderinfo(userId,goodsId,goodsName,goodsCount,goodsPrice,orderChannel,status,createDate)values(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn="id",keyProperty="id",before = false,resultType = long.class,statement = "select last_insert_id()")
    public long insertorderInfo(OrderInfo orderInfo);
    @Insert("insert into miaoshaorder(userId,orderId,goodsId)values(#{userId},#{orderId},#{goodsId})")
    public int insertmiaoshaOrder(MiaoshaOrder miaoshaOrder);
}

