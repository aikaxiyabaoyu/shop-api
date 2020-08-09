package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service("memberService")
public class MemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

//    @Autowired
//    private MQSender mqSender;

    @Override
    public ServerResponse addMember(Member member) {

        memberMapper.addMember(member);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(String memberName, String password) {
        //判断用户名或密码是否为空
        if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){

            return ServerResponse.error(ResponseEnum.LOGIN_NAME_PASSWORD_IS_NULL);
        }

        //判断用户是否存在
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(queryWrapper);
        if(member == null){

            return ServerResponse.error(ResponseEnum.LOGIN_NAME_IS_NOT_EMPTY);
        }

        //判断密码是否正确
        if(!password.equals(member.getPassword())){

            return ServerResponse.error(ResponseEnum.LOGIN_PASSWORD_IS_ERROR);
        }

        //======================生成token=======================
        //模拟JWT[JSON WEB TOKEN]
        //生成token的样子类似与xxxx.yyy 用户信息.对用户信息的签名
        //签名的目的：保证用户信息不被篡改
        //怎么生成签名：md5(用户信息 结合 秘钥)
        //sign代表签名，secret/
        //秘钥是在服务器端保存的，黑客，攻击者不好获取到
        //======================================================

        //生成用户信息对应的json
        MemberVo memberVo = new MemberVo();
        Long memberId = member.getId();
        memberVo.setId(member.getId());
        memberVo.setMemberName(member.getMemberName());
        memberVo.setRealName(member.getRealName());
        String uuid = UUID.randomUUID().toString();
        memberVo.setUuid(uuid);
        //将java对象转为json
        String memberJson = JSONObject.toJSONString(memberVo);

        //对用户信息进行base64编码【起到一定的安全作用但是对于计算机专业人士起不到作用】
        try {
            String memberJsonBase64 = Base64.getEncoder().encodeToString(memberJson.getBytes("utf-8"));
            //生成用户信息所对应的签名
            String sign = Md5Util.sign(memberJsonBase64, Md5Util.SECRET);
            //对签名也进行base64编码
            String signBase64 = Base64.getEncoder().encodeToString(sign.getBytes("utf-8"));

            //处理超时
            RedisUtil.setEx(KeyUtil.buildMemberKey(uuid,memberId),"",KeyUtil.MEMBER_KEY_EXPIRE);

            //发送邮件
//            MQMail mail = new MQMail();
//            mail.setMail(member.getMail());
//            mail.setRealName(member.getRealName());
//            mail.setTitle("登录成功！");
//            mail.setContent(member.getRealName() + " 在 "+ DateUtil.date2string(new Date() , DateUtil.Y_M_D_H_M_S) + " 登录 ！！！");
//            mqSender.sendMails(mail);

            //响应数据给客户端
            return ServerResponse.success(memberJsonBase64+"."+signBase64);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }
}
