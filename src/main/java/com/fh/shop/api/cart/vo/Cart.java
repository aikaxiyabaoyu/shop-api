package com.fh.shop.api.cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fh.shop.api.util.BigDecimalJackson;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    // 总价格
    @JsonSerialize(using = BigDecimalJackson.class)
    private BigDecimal totalPrice;
    // 总数量
    private Integer totalNum;
    // 商品集合
    private List<CartItem> itemList = new ArrayList<>();

}
