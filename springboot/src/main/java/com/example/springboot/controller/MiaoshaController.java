package com.example.springboot.controller;

import com.example.springboot.bean.MiaoshaOrder;
import com.example.springboot.bean.OrderInfo;
import com.example.springboot.bean.User;
import com.example.springboot.service.GoodsService;
import com.example.springboot.service.MiaoshaService;
import com.example.springboot.service.OrderService;
import com.example.springboot.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;


    /**
     * 秒杀模块
     * @param user
     * @param model
     * @param goodsId
     * @return
     */
    @RequestMapping("/do_miaosha")
    public String do_miaosha(User user,
                             Model model,
                             @Param("goodsId")Integer goodsId){
        //判断有没有登录，没有登录请登录
        model.addAttribute("user",user);
        if (user==null)
        {
            return "login";
        }
       //判断被秒杀的商品是否还有库存
       GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        if (goods.getStockCount()<=0)
        {
            model.addAttribute("errmsg","该商品已被秒杀完，没有库存啦");
              return "maiosha_fail";
        }


        //判断该用户是否秒杀过了该商品

        MiaoshaOrder miaoshaOrder=orderService.getMiaoshaUseridAndgoodsid(user.getId(),goodsId);
        if (miaoshaOrder!=null)
        {
            model.addAttribute("errmsg","你已经秒杀过该商品，你不能再秒杀");
            return "maiosha_fail";

        }

        //秒杀的业务逻辑 ----库存减少，下订单，写入秒杀订单
        OrderInfo orderInfo=miaoshaService.miaosha(user,goods);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";

    }
}
