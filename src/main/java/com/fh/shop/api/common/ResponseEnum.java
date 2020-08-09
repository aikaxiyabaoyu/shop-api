package com.fh.shop.api.common;

public enum ResponseEnum {

    CART_NUM_IS_ERROR(3002,"商品数量不合法"),
    CART_PRODUCT_IS_DOWN(3001,"商品已下架"),
    CART_PRODUCT_IS_NULL(3000,"添加的商品不存在"),

    HANDER_IS_EXITS(4000 , "头信息不存在"),
    HANDER_IS_MISS(4001 , "头信息丢失"),
    HANDER_IS_TAMPER(4002 , "头信息被篡改"),
    HANDER_IS_TIME_OUT(4003 , "头信息已超时"),

    NAME_PASSWORD_MAIL_PHONE(1000,"请输入完整信息！"),
    MEMBER_NAME_EXIST(1001,"用户名已存在！"),
    MEMBER_PHONE_EXIST(1003,"手机号已存在！"),
    MEMBER_MAIL_EXIST(1005,"邮箱已存在！"),
    NAME_IS_NOT(1000 , "请输入正确的昵称"),
    PHONE_IS_NOT(1002 , "请输入正确的手机号"),
    MAIL_IS_NOT(1004 , "请输入正确的邮箱"),

    LOGIN_NAME_PASSWORD_IS_NULL(2000 ,"用户名或密码为空"),
    LOGIN_NAME_IS_NOT_EMPTY(2001 ,"用户不存在"),
    LOGIN_PASSWORD_IS_ERROR(2002 ,"密码错误")
    ;

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
