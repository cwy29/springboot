package com.example.springboot.dao;

import com.example.springboot.bean.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.springboot.vo.GoodsVo;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {
    /**
     * 商品列表
     * @return
     */
    @Select("select g.*,mg.miaoshaPrice,mg.stockCount,mg.startDate,mg.endDate from miaoshagoods mg left join goods g on g.id=mg.goodsId")
    public List<GoodsVo> goodsVoList();
    /**
     * 单个商品列表
     * @return
     */
    @Select("select g.*,mg.miaoshaPrice,mg.stockCount,mg.startDate,mg.endDate from miaoshagoods mg left join goods g on mg.goodsId=g.id where g.id=#{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(Integer goodsId);

    /**
     * 减少商品可秒杀库存
     * @param g
     * @return
     */
    @Update("update miaoshagoods set stockCount=stockCount-1 where goodsId=#{goodsId} ")
    public int reduceStock(MiaoshaGoods g);
}
