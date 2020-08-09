package com.fh.shop.api.cart.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;

import com.fh.shop.api.member.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
@Api(tags = "购物车接口")
public class CartController {

    @Resource(name = "cartService")
    private ICartService cartService;

    @PostMapping("addItem")
    @Check
    @ApiOperation("新增商品到购物车的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth" , value = "头信息" , type = "string" , required = true , paramType = "header"),
            @ApiImplicitParam(name = "goodsId" , value = "添加购物车商品的 id" , type = "string" , required = true , paramType = "query"),
            @ApiImplicitParam(name = "num" , value = "添加购物车商品的数量" , type = "int" , required = true , paramType = "query")
    })
    public ServerResponse addItem(HttpServletRequest request,Long goodsId,int num){
        // 获取当前登录会员信息
        MemberVo memberInfo = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        return cartService.addItem(memberInfo.getId(),goodsId,num);
    }

    @GetMapping("/findCart")
    @Check
    @ApiOperation("获取指定会员的购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header")
    })
    public ServerResponse findCart(HttpServletRequest request){
        // 获取当前登录会员信息
        MemberVo memberInfo = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        return cartService.findItemList(memberInfo.getId());
    }

    @PostMapping("/deleteCartItem")
    @Check
    @ApiOperation("删除购物车中指定的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId",value = "删除购物车商品的Id",type = "string",required = true,paramType = "query"),
            @ApiImplicitParam(name = "x-auth",value = "头信息",type = "string",required = true,paramType = "header")
    })
    public ServerResponse deleteCartItem(HttpServletRequest request,Long goodsId){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);

        return cartService.deleteCartItem(member.getId(),goodsId);
    }
}
