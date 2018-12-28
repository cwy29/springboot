package com.example.springboot.vo;

import java.util.Date;

import com.example.springboot.bean.Goods;

/**
 * 联合查询
 */
public class GoodsVo extends Goods {
	//秒杀价
	private Double miaoshaPrice;
	//商品被秒杀的库存数量
	private Integer stockCount;
	//秒杀开始时间
	private Date startDate;
	//秒杀结束时间
	private Date endDate;

	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getMiaoshaPrice() {
		return miaoshaPrice;
	}
	public void setMiaoshaPrice(Double miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}
}
