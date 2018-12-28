package com.example.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*@Controller
public class SuccessBackController {
    @RequestMapping("/successback")
    private void SuccessBack(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String r1_Code=request.getParameter("r1_Code");
        PrintWriter pw=response.getWriter();
        if("1".equals(r1_Code))
        {
            String p1_MerId=request.getParameter("p1_MerId");
            String r3_Amt=request.getParameter("r3_Amt");
            String r6_Order=request.getParameter("r6_Order");
            String rp_PayDate=request.getParameter("rp_PayDate");
            pw.println("<h2>支付成功！</h2><br/>"
                    + "<h2>商户编号："+p1_MerId+"</h2><br/>"
                    + "<h2>支付金额："+r3_Amt+"</h2><br/>"
                    +"<h2>商户订单号："+r6_Order+"</h2><br/>"
                    +"<h2>支付成功时间："+rp_PayDate+"</h2><br/>");
        }
        else
        {
            pw.println("支付失败！");
        }
    }
}*/
@Controller
public class SuccessBackController {
    @RequestMapping("/successback")
    private ModelAndView SuccessBack(HttpServletRequest request,
                                     ModelAndView mv) throws Exception {

        String r1_Code=request.getParameter("r1_Code");

        if("1".equals(r1_Code))
        {
            mv=new ModelAndView("PaySuccess");

            String p1_MerId=request.getParameter("p1_MerId");
            String r3_Amt=request.getParameter("r3_Amt");
            String r6_Order=request.getParameter("r6_Order");
            String rp_PayDate=request.getParameter("rp_PayDate");


            //商户编号
            mv.addObject("p1_MerId",p1_MerId);
            //支付金额
            mv.addObject("r3_Amt",r3_Amt);
            //商户订单号
            mv.addObject("r6_Order",r6_Order);
            //支付成功时间
            mv.addObject("rp_PayDate",rp_PayDate);

            return mv;
        }
        else
        {
            mv=new ModelAndView("PayFail");
            return mv;
        }
    }
}