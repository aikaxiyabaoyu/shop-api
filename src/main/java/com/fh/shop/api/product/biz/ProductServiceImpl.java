package com.fh.shop.api.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductMapper productMapper;

    @Override
    public ServerResponse queryHotList() {
        String hotListStr = RedisUtil.get("hotList");
        if(StringUtils.isNotEmpty(hotListStr)){
            List<Product> hotList = JSONObject.parseArray(hotListStr, Product.class);

            return ServerResponse.success(hotList);
        }

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("isHot",1);
        queryWrapper.eq("isUp",1);

        List<Product> productList = productMapper.selectList(queryWrapper);
        RedisUtil.set("hotList",JSONObject.toJSONString(productList));

        return ServerResponse.success(productList);
    }
}
