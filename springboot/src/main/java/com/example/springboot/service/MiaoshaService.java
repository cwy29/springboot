package com.example.springboot.service;

import com.example.springboot.bean.OrderInfo;
import com.example.springboot.bean.User;
import com.example.springboot.dao.GoodsDao;
import com.example.springboot.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;//商品
    @Autowired
    OrderService orderService;//订单

    /**
     * 减少商品可秒杀的库存   生成订单
     * @param user
     * @param goods
     * @return
     */
    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goods) {
        //减少商品可秒杀的库存
        goodsService.reduceStock(goods);
        //生成订单 涉及到OrderInfo和MiaoshaOrder
        return orderService.createOrder(user,goods);




    }
}
