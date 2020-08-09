package com.fh.shop.api.product.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product implements Serializable {

    @TableId(type = IdType.AUTO,value = "productId")
    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private Long brandId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enteringDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private String fileName;

    private int isHot;

    private int isUp;

    private Long bigId;

    private Long middleId;

    private Long smallId;

}
