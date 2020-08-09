package com.fh.shop.api.member.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Resource(name = "memberService")
    private IMemberService memberService;

    @PostMapping
    public ServerResponse addMember(Member member){

        return memberService.addMember(member);
    }

    @GetMapping("/login")
    public ServerResponse login(String memberName,String password){

        return memberService.login(memberName,password);
    }

    @GetMapping("/findMember")
    @Check
    public ServerResponse findMember(HttpServletRequest request){
        MemberVo member = (MemberVo)request.getAttribute(SystemConstant.CURR_MEMBER);

        return ServerResponse.success(member);
    }

    @GetMapping("/logout")
    @Check
    public ServerResponse logout(HttpServletRequest request){
        MemberVo member = (MemberVo)request.getAttribute(SystemConstant.CURR_MEMBER);
        Long id = member.getId();
        String uuid = member.getUuid();

        String key = KeyUtil.buildMemberKey(uuid, id);
        RedisUtil.del(key);

        return ServerResponse.success();
    }
}
