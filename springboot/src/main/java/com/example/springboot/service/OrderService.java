package com.example.springboot.service;

import com.example.springboot.bean.MiaoshaOrder;
import com.example.springboot.bean.OrderInfo;
import com.example.springboot.bean.User;
import com.example.springboot.dao.OrderDao;
import com.example.springboot.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    /**
     * 得到参与秒杀用户的ID和秒杀商品的ID
     * @param userid
     * @param goodsId
     * @return
     */
     @Select("select * from miaoshaorder where id=#{userid}and goodsId=#{goodsId}")
    public MiaoshaOrder getMiaoshaUseridAndgoodsid(@Param("userid") int userid, @Param("goodsId") Integer goodsId)
    {
        return orderDao.getMiaoshaUseridAndgoodsid(userid,goodsId);
    }

    /**
     * 生成订单 涉及到OrderInfo和MiaoshaOrder
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goods) {

        OrderInfo orderInfo=new OrderInfo();

        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setUserId(user.getId());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);//0表示未支付状态
        long orderid= orderDao.insertorderInfo(orderInfo);
        MiaoshaOrder miaoshaOrder=new MiaoshaOrder();
        miaoshaOrder.setOrderId(orderid);
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setGoodsId(goods.getId());
        orderDao.insertmiaoshaOrder(miaoshaOrder);
        return orderInfo;

    }
}
