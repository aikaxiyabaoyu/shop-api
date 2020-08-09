//package com.fh.shop.api.rabbitmq;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fh.shop.api.common.SystemConstant;
//import com.fh.shop.api.util.MailUtil;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MQReceiver {
//
//    @Autowired
//    private MailUtil mailUtil;
//
//    @RabbitListener(queues = SystemConstant.QUEUE)
//    public void handleMailMessage(String msg ){
//        MQMail mqMail = JSONObject.parseObject(msg, MQMail.class);
//        mailUtil.sendMail(mqMail.getMail() , mqMail.getTitle() , mqMail.getContent());
//    }
//
//}
