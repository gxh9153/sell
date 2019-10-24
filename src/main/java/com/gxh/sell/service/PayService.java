package com.gxh.sell.service;

import com.gxh.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {
    PayResponse create(OrderDTO orderDTO);
}
