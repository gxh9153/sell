package com.gxh.sell.controller;

import com.gxh.sell.dto.OrderDTO;
import com.gxh.sell.enums.ResultEnums;
import com.gxh.sell.exceptions.SellException;
import com.gxh.sell.service.OrderService;
import com.gxh.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnums.ORDER_NOT_EXIT);
        }
        //发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        return new ModelAndView("pay/create",map);
    }
}
