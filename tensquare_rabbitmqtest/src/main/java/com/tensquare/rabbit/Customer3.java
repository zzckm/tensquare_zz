package com.tensquare.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "heima")//需要指定消费的那个队列
public class Customer3 {

    @RabbitHandler
    public  void  getMessage(String messsage){
        System.out.println("heima接收消息："+messsage);
    }

}
