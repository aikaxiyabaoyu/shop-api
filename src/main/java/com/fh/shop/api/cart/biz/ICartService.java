package com.fh.shop.api.cart.biz;

import com.fh.shop.api.common.ServerResponse;

public interface ICartService {

    ServerResponse addItem(Long memberId, Long goodsId, int num);

    ServerResponse findItemList(Long id);

    ServerResponse deleteCartItem(Long id, Long goodsId);
}
