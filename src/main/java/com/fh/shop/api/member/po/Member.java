package com.fh.shop.api.member.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "t_member")
public class Member implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String memberName;

    private String password;

    private String realName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String mail;

    private String phone;
}
