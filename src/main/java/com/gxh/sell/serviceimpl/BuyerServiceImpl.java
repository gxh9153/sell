package com.gxh.sell.serviceimpl;

import com.gxh.sell.dto.OrderDTO;
import com.gxh.sell.enums.ResultEnums;
import com.gxh.sell.exceptions.SellException;
import com.gxh.sell.service.BuyerService;
import com.gxh.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return CheckOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancel(String openid, String orderId) {
        OrderDTO orderDTO = CheckOrderOwner(openid, orderId);
        if(orderDTO == null){
            log.error("【取消订单】查不到该订单 orderId={}",orderId);
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        return orderService.cancel(orderDTO);
    }
    private OrderDTO CheckOrderOwner(String openid,String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【订单详情】微信openid不能为空");
            throw new SellException(ResultEnums.OPENID_EMPTY);
        }
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        //判断是否是自己的订单
        if(!orderDTO.getOrderId().equalsIgnoreCase(orderId)){
            log.error("【订单详情】订单的openid不一致 openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnums.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
