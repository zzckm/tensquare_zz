package com.tensquare.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")//需要指定消费的那个队列
public class Customer2 {

    @RabbitHandler
    public  void  getMessage(String messsage){
        System.out.println("itcast接收消息："+messsage);
    }

}
