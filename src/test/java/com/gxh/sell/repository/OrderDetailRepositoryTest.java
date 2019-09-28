package com.gxh.sell.repository;

import com.gxh.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("000001");
        orderDetail.setOrderId("000001");
        orderDetail.setProductId("000001");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(5.7));
        orderDetail.setProductQuantity(2);
        orderDetail.setProductIcon("http://www.jpg");

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result = repository.findByOrderId("000001");
        Assert.assertNotEquals(0,result.size());
    }
}