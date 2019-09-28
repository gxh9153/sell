package com.gxh.sell.service;

import com.gxh.sell.dto.OrderDTO;

public interface BuyerService {

     OrderDTO findOrderOne(String openid,String orderId);

     OrderDTO cancel(String openid,String orderId);
}
