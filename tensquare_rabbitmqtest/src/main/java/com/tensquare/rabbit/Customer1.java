package com.tensquare.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component//添加为容器里
@RabbitListener(queues = "zhiyou100")//监听器——需要指定消费的那个队列
public class Customer1 {

    @RabbitHandler
    public  void  getMessage(String messsage){
        System.out.println("zhiyou接收消息："+messsage);
    }

}
