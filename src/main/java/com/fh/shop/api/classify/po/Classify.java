package com.fh.shop.api.classify.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Classify implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private Long pid;

}
