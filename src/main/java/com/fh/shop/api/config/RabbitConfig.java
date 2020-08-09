//package com.fh.shop.api.config;
//
//import com.fh.shop.api.common.SystemConstant;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
////@Configuration
//public class RabbitConfig {
//
//    // 创建交换机
//    @Bean
//    public DirectExchange mailExchange(){
//        return new DirectExchange(SystemConstant.EXCHANGE, true , false);
//    }
//
//    // 创建消息队列
//    @Bean
//    public Queue mailQueue(){
//        return new Queue(SystemConstant.QUEUE , true , false , false);
//    }
//
//    @Bean
//    public Binding mailBinding(){
//        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(SystemConstant.ROUTER_KEY);
//    }
//
//}
