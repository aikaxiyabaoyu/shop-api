package com.fh.shop.api.cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.shop.api.util.BigDecimalJackson;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartItem implements Serializable {

    private Long id;
    // 商品名
    private String goodsName;
    // 商品单价
    @JsonSerialize(using = BigDecimalJackson.class)
    private BigDecimal price;
    // 商品数量
    private Integer num;
    // 商品小计
    @JsonSerialize(using = BigDecimalJackson.class)
    private BigDecimal subPrice;
    // 商品图片
    private String image;

}
