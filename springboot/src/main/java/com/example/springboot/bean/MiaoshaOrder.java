package com.example.springboot.bean;

public class MiaoshaOrder {
	private Long id;
	//秒杀用户的ID
	private int userId;
	//秒杀的订单ID
	private Long  orderId;
	//秒杀的商品ID
	private Long goodsId;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
}
