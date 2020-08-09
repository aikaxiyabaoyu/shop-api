//package com.fh.shop.api.rabbitmq;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fh.shop.api.common.SystemConstant;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MQSender {
//
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    public void sendMail(String info){
//        amqpTemplate.convertAndSend(SystemConstant.EXCHANGE , SystemConstant.ROUTER_KEY , info);
//    }
//
//    public void sendMails(MQMail mqMail){
//        String string = JSONObject.toJSONString(mqMail);
//        amqpTemplate.convertAndSend(SystemConstant.EXCHANGE , SystemConstant.ROUTER_KEY , string);
//    }
//
//}
