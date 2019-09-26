package com.gxh.sell.serviceimpl;

import com.gxh.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
        ProductInfo result = productInfoService.findOne("000001");
        Assert.assertEquals("000001",result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> result = productInfoService.findAll(pageRequest);
        //System.out.println(result.getTotalElements());
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("000002");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(10.2));
        productInfo.setProductStock(200);
        productInfo.setProductDescription("一种虾壳好剥又好吃的虾");
        productInfo.setProductIcon("http://wwe.png");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(2);
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotNull(result);
    }
}