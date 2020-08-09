package com.fh.shop.api.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.member.po.Member;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberMapper extends BaseMapper<Member> {

    @Insert("insert into t_member (memberName,password,realName,birthday,mail,phone) values (#{memberName},#{password},#{realName},#{birthday},#{mail},#{phone})")
    void addMember(Member member);

}
