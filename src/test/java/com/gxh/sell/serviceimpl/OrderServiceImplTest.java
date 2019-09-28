package com.gxh.sell.serviceimpl;

import com.gxh.sell.dataobject.OrderDetail;
import com.gxh.sell.dto.OrderDTO;
import com.gxh.sell.enums.OrderStatusEnums;
import com.gxh.sell.enums.PayStatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID="10011";
    private final String ORDER_ID="156949934871465766";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("熊赞");
        orderDTO.setBuyerPhone("18370389153");
        orderDTO.setBuyerAddress("万达广场");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1= new OrderDetail();
        o1.setProductId("000003");
        o1.setProductQuantity(2);

        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 result ={}",result);
        Assert.assertNotNull(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> result = orderService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnums.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnums.SUCCESS.getCode(),result.getPayStatus());
    }
}