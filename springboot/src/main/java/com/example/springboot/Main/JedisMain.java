package com.example.springboot.Main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisMain {

    public boolean TransactionTest() throws InterruptedException {
        int  balance; //余有金额
        int  debt;//所欠金额
        int  paymoney=10;//支付金额
        //连接jedis
        Jedis jedis=new Jedis("127.0.0.1",6379);
        //监控balance，以防别人篡改balance
        jedis.watch("balance");
        //获取key对应的value值
        balance=Integer.parseInt(jedis.get("balance"));
        debt=Integer.parseInt(jedis.get("debt"));

        //模拟高并发，网络延时
        Thread.sleep(5000);

        //判断二者之间的大小
        if (balance<paymoney)
        {
            //放弃监控
            jedis.unwatch();
            System.out.println("注意提示：你的金额不足，请及时充值");
            return false;
        }
        else{
            //开启事物
            Transaction transaction=jedis.multi();
            //余有金额减去支付金额
            transaction.decrBy("balance",paymoney);
            //所欠金额加上支付金额
            transaction.incrBy("debt",paymoney);
            //执行事物的操作
            transaction.exec();
            //再次重新获取key对应的value值
            balance=Integer.parseInt(jedis.get("balance"));
            debt=Integer.parseInt(jedis.get("debt"));
            //输出结果
            System.out.println("你的余额："+balance+"元");
            System.out.println("所欠余额："+debt+"元");
            return true;
        }




    }




    public static void main(String[]args) throws InterruptedException {
        /*Jedis jedis=new Jedis("127.0.0.1",6379);
        //测试jedis是否连通
        System.out.println(jedis.ping());
        //jedis.multi()开启事物   设置key value
        Transaction transaction=jedis.multi();
        transaction.set("age","18");
        transaction.set("username","陈伟勇");
        transaction.exec();
        //取出value值
        System.out.println(jedis.get("username"));*/


        JedisMain jedisMain=new JedisMain();
        boolean flag=jedisMain.TransactionTest();
        if (flag)
        {
            System.out.println("交易成功");
        }
        else {
            System.out.println("交易失败");
        }



    }
}
