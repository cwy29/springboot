package com.example.springboot.service;

import com.example.springboot.bean.MiaoshaGoods;
import com.example.springboot.bean.OrderInfo;
import com.example.springboot.dao.GoodsDao;
import com.example.springboot.dao.OrderDao;
import com.example.springboot.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    /**
     * 商品列表
     * @return
     */
    public List<GoodsVo> goodsVoList(){
        return  goodsDao.goodsVoList();
    }
    /**
     * 单个商品详情
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(Integer goodsId)
    { return goodsDao.getGoodsVoByGoodsId(goodsId); }

    /**
     * 减少商品可秒杀的库存
     * @param goods
     */
    public void reduceStock(GoodsVo goods) {
        MiaoshaGoods g=new MiaoshaGoods();
        g.setGoodsId(goods.getId());//生成可秒杀的商品ID
        goodsDao.reduceStock(g);
    }

}

