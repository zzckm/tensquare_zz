package com.test;

import com.tensquare.rabbit.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//表示在测试发生时，自动启动启动类
@RunWith(SpringRunner.class) //替代Junit原生的运行器
@SpringBootTest(classes = RabbitApplication.class)//指定程序入口可以放配置文件//也可以放main函数静态启动类//测试入口
public class ProduceTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    public void testSend1(){                      //队列名称
        rabbitTemplate.convertAndSend("zhiyou100","直接模式测试");
    }

    /**
     * 分裂模式
     */
    @Test
    public void testSend2(){                      //队列名称
        rabbitTemplate.convertAndSend("chuanzhi","","分裂模式测试");
    }

    /**
     * 主题模式
     */
    @Test
    public void testSendTopic(){                      //队列名称
        rabbitTemplate.convertAndSend("topic37","good.log","主题模式测试");
    }
}
