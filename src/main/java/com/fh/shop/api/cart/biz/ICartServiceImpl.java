package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IProductMapper productMapper;

    @Override
    public ServerResponse addItem(Long memberId, Long goodsId, int num) {
        //判断商品是否存在
        Product product = productMapper.selectById(goodsId);
        if(null==product){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_NULL);
        }
        //商品的状态是否正常
        if(product.getIsUp() == 0){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_DOWN);
        }

        //如果会员已经有了对应的购物车
        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        if(StringUtils.isNotEmpty(cartJson)){
            //直接向购物车放入商品

            Cart cart = JSONObject.parseObject(cartJson, Cart.class);
            List<CartItem> cartItemList = cart.getItemList();
            //在购物车中查找对应的商品
            CartItem cartItem = null;
            for (CartItem item : cartItemList) {
                if (item.getId().longValue() == goodsId.longValue()){
                    cartItem = item;
                    break;
                }
            }

            if(cartItem!=null){
                //如果商品存在更新商品的数量和小计，更新购物车【总个数，总计】
                //更新商品的数量
                cartItem.setNum(cartItem.getNum()+num);
                int num1 = cartItem.getNum();
                if(num1 <=0){
                    //删除整个商品
                    cartItemList.remove(cartItem);
                }
                BigDecimal subPrice = BigDecimalUtil.mul(num1 + "", cartItem.getPrice().toString());
                //更新小计
                cartItem.setSubPrice(subPrice);
                //更新购物车
                updateCart(memberId, cart);
            }else {
                //如果商品不存在，添加商品更新购物车【总个数，总计】
                if(num <= 0){
                    return ServerResponse.error(ResponseEnum.CART_NUM_IS_ERROR);
                }
                //构建商品
                CartItem cartItemInfo = buildCartItem(num, product);
                //加入购物车
                cart.getItemList().add(cartItemInfo);
                //更新购物车
                updateCart(memberId, cart);
            }

        }else{
            //如果没有对应的购物车
            Cart cart = new Cart();
            List<CartItem> cartItemList = cart.getItemList();

            //创建购物车再添加商品，更新购物车
            //构建商品
            CartItem cartItemInfo = buildCartItem(num, product);
            //加入购物车
            cart.getItemList().add(cartItemInfo);
            //更新购物车
            updateCart(memberId, cart);
        }

        return ServerResponse.success();
    }

    @Override
    public ServerResponse findItemList(Long memberId) {
        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        return ServerResponse.success(cart);
    }

    private CartItem buildCartItem(int num, Product product) {
        CartItem cartItemInfo = new CartItem();
        cartItemInfo.setId(product.getProductId());
        cartItemInfo.setPrice(product.getProductPrice());
        cartItemInfo.setImage(product.getFileName());
        cartItemInfo.setGoodsName(product.getProductName());
        cartItemInfo.setNum(num);
        BigDecimal subPrice = BigDecimalUtil.mul(num + "", product.getProductPrice().toString());
        cartItemInfo.setSubPrice(subPrice);
        return cartItemInfo;
    }

    private void updateCart(Long memberId, Cart cart) {
        List<CartItem> cartItemList = cart.getItemList();
        int totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        String cartKey = KeyUtil.buildCartKey(memberId);
        if(cartItemList.size() == 0){
            //删除整个购物车
            RedisUtil.del(cartKey);
            return;
        }
        //更新购物车
        for (CartItem item : cartItemList) {
            totalCount += item.getNum();
            totalPrice = BigDecimalUtil.add(totalPrice.toString(), item.getSubPrice().toString());
        }

        cart.setTotalNum(totalCount);
        cart.setTotalPrice(totalPrice);
        //往redis中更新
        String cartNewJson = JSONObject.toJSONString(cart);

        RedisUtil.set(cartKey, cartNewJson);
    }

    @Override
    public ServerResponse deleteCartItem(Long id, Long goodsId) {
        //判断商品是否存在
        Product product = productMapper.selectById(goodsId);
        if(null == product){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_NULL);
        }
        //商品的状态是否正常
        if(product.getIsUp() == 0){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_DOWN);
        }

        //先从redis中取出会员对应的购物车
        String cartKey = KeyUtil.buildCartKey(id);
        Cart cart = JSONObject.parseObject(RedisUtil.get(cartKey), Cart.class);
        //循环购物车中的商品 找到要删除的商品 在集合中删除
        List<CartItem> cartItemList = cart.getItemList();
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getId().longValue() == goodsId.longValue()){
                cartItemList.remove(cartItem);
                break;
            }
        }

        //当购物车中没有商品时 删除整个购物车
        if(cartItemList.size() == 0){
            RedisUtil.del(cartKey);

            return ServerResponse.success();
        }

        //更新redis中的购物车
        String cartJson = JSONObject.toJSONString(cart);

        RedisUtil.set(cartKey,cartJson);

        return ServerResponse.success();
    }
}
