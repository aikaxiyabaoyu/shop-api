package com.fh.shop.api.rabbitmq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MQMail implements Serializable {

    private String mail;

    private String realName;

    private String title;

    private String content;
}
