package com.example.springboot.controller;

import com.example.springboot.bean.MiaoshaUser;
import com.example.springboot.bean.User;
import com.example.springboot.redis.GoodsKey;
import com.example.springboot.redis.RedisService;
import com.example.springboot.service.GoodsService;
import com.example.springboot.service.UserService;
import com.example.springboot.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {

     @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;




    /**
     * 页面缓存的方式
     * 商品列表
     * @return
     *//*
    @RequestMapping(value="/to_list", produces="text/html")
    @ResponseBody
    public String list(HttpServletRequest request,
                       HttpServletResponse response,
                       Model model,
                       MiaoshaUser user) {
        model.addAttribute("user", user);
        //取缓存
//    	String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
//    	if(!StringUtils.isEmpty(html)) {
//    		return html;
//    	}
        List<GoodsVo> goodsList = goodsService.goodsVoList();
        model.addAttribute("goodsList", goodsList);
//    	 return "goods_list";
        SpringWebContext ctx = new SpringWebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );
        //手动渲染
        String html = thymeleafViewResolver.getTemplateEngine().process("userlist", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }




*/

    /**
     * 商品列表
     * @return
     */
    @RequestMapping("/goodsVoList")
    public String goodsVoList(User user,Model model){

        user=userService.getAllUsers(user.getUsername(),user.getUserpwd());
        if (user!=null)
        {
            List<GoodsVo> goodsList=goodsService.goodsVoList();
            model.addAttribute("goodsList",goodsList);
            model.addAttribute("username",user.getUsername());
            return "userlist";
        }



        return "login_fail";



    }
    /**
     * 单个商品列表
     * @return
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String detail(@PathVariable("goodsId")Integer goodsId,
                         User user, Model model){


        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);

        //秒杀的开始时间
        long startAt=goods.getStartDate().getTime();
        //秒杀的结束时间
        long endAt =goods.getEndDate().getTime();
        //当前时间
        long now=System.currentTimeMillis();

        //根据当前时间与秒杀的开始时间和秒杀的结束时间进行比较判断秒杀状态
        // 0：表示还没开始秒杀  1：表示处于秒杀中 2：表示秒杀已结束
        int miaoshaStatus = 0;
        //距离秒杀还有多少时间提醒
        // 具体时间：还没开始秒杀，准备秒杀    0：处于秒杀中      -1：秒杀结束
        int remainSeconds = 0;

        if (now<startAt){
            miaoshaStatus=0;//还没开始秒杀
            //距离秒杀还有多少毫秒
            remainSeconds=(int)((startAt-now)/1000);
        }
        else if (now>endAt){
            miaoshaStatus=2;//秒杀已结束
            remainSeconds=-1;
        }
        else {
            miaoshaStatus=1;//处于秒杀中
            remainSeconds=0;
        }
        //将用户，秒杀状态，秒杀时间提醒，单个商品 添加到model中
        model.addAttribute("user",user);
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("goods",goods);




        return "goods_detail";


    }
}
