package com.gxh.sell.serviceimpl;

import com.gxh.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory result = categoryService.findOne(1);
        //Assert 断言，比较实际的值和用户预期的是否一样
        Assert.assertEquals(new Integer(1),result.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> result = categoryService.findAll();
        Assert.assertNotEquals(null,result.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(null,result.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("女生最爱", 5);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}