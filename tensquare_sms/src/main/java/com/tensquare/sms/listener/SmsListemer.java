package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.ws.soap.Addressing;
import java.util.Map;

/*
短信监听类
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListemer {
    /**
     * 发送短息(消费者监听)
     */
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;



    @RabbitHandler
    public void sendSms(Map<String,String> message){
        String mobile= message.get("mobile");
        String checkCode= message.get("checkcode");
        System.out.println("手机："+mobile);
        System.out.println("验证码："+checkCode);
        try {                                                       //code名称需要与阿里云的自填的名称一致
            smsUtil.sendSms(mobile,template_code,sign_name,"{\"code\":\""+ checkCode+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    }
